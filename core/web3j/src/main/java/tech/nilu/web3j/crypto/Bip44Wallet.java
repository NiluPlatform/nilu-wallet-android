package tech.nilu.web3j.crypto;

import com.google.protobuf.ByteString;

import org.spongycastle.asn1.sec.SECNamedCurves;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.crypto.digests.RIPEMD160Digest;
import org.spongycastle.crypto.digests.SHA512Digest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.params.KeyParameter;
import org.web3j.crypto.Hash;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Bip44Wallet {
    private static final int MAX_CHILD_DERIVATION_ATTEMPTS = 100;
    private static final X9ECParameters curve = SECNamedCurves.getByName("secp256k1");
    private static BigInteger RAND_INT = new BigInteger(256, new SecureRandom());

    private HDSeed seed;
    private String filename;
    private String mnemonic;
    private HDKeyChain chain;
    private byte[] mnemonicBytes;
    private SecureRandom secureRandom = new SecureRandom();

    public Bip44Wallet(String pass) {
        this(Bip39Locale.ENGLISH, pass);
    }

    public Bip44Wallet(Bip39Locale lang, String pass) {
        byte[] initialEntropy = new byte[16];
        this.secureRandom.nextBytes(initialEntropy);

        this.mnemonic = MnemonicUtils.generateMnemonic(lang, initialEntropy);
        this.mnemonicBytes = MnemonicUtils.generateSeed(mnemonic, pass);
        this.seed = new HDSeed(MiscUtil.checkNotNull(mnemonicBytes), System.currentTimeMillis() / 1000);
        this.chain = new HDKeyChain(seed);
    }

    public Bip44Wallet(String pass, String nemonic) {
        this.mnemonic = nemonic;
        this.mnemonicBytes = MnemonicUtils.generateSeed(mnemonic, pass);
        this.seed = new HDSeed(MiscUtil.checkNotNull(mnemonicBytes), System.currentTimeMillis() / 1000);
        this.chain = new HDKeyChain(seed);
    }

    public HDKey createRootKey() {
        return createKey("M/44H/60H/0H/0");
    }

    public HDKey createKey(String path) {
        List<ChildNumber> keyPath = HDUtil.parsePath(path);

        return chain.getKeyByPath(keyPath, true);
    }

    public HDKey deriveChild(HDKey parent, int index, boolean hardened) {
        return chain.deriveChild(parent, index, hardened);
    }

    public HDKey deriveNextChild(HDKey parent, boolean hardened) {
        return chain.deriveChild(parent, nextChildIndex(parent), hardened);
    }

    public int countChildren(HDKey parent) {
        return chain.countChildren(parent.childNumberPath);
    }

    public int nextChildIndex(HDKey parent) {
        return chain.getNextChildNumber(parent.childNumberPath, true).num();
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    static class HDUtil {
        static HDKey deriveChildKey(HDKey parent, ChildNumber childNumber) throws HDWalletException {
            RawKeyBytes rawKey = deriveChildKeyBytesFromPrivate(parent, childNumber);
            return new HDKey(
                    append(parent.getPath(), childNumber),
                    rawKey.chainCode,
                    new BigInteger(1, rawKey.keyBytes),
                    parent
            );
        }

        static RawKeyBytes deriveChildKeyBytesFromPrivate(HDKey parent, ChildNumber childNumber) throws HDWalletException {
            MiscUtil.checkArgument(parent.getPriv() != null, "Parent key must have private key bytes for this method.");
            byte[] parentPublicKey = parent.getPub();
            MiscUtil.checkState(parentPublicKey.length == 33, "Parent pubkey must be 33 bytes, but is " + parentPublicKey.length);
            ByteBuffer data = ByteBuffer.allocate(37);
            if (childNumber.isHardened()) {
                data.put(parent.getPrivKeyBytes33());
            } else {
                data.put(parentPublicKey);
            }

            data.putInt(childNumber.i());
            byte[] i = HashUtil.hmacSha512(HashUtil.createHmacSha512Digest(parent.getChainCode()), data.array());
            MiscUtil.checkState(i.length == 64, i.length);
            byte[] il = Arrays.copyOfRange(i, 0, 32);
            byte[] chainCode = Arrays.copyOfRange(i, 32, 64);
            BigInteger ilInt = new BigInteger(1, il);
            if (ilInt.compareTo(curve.getN()) > 0) {
                throw new HDWalletException("Illegal derived key: I_L >= n");
            }
            BigInteger priv = parent.getPriv();
            BigInteger ki = priv.add(ilInt).mod(curve.getN());
            if (ki.compareTo(new BigInteger("0")) == 0) {
                throw new HDWalletException("Illegal derived key: derived private key equals 0.");
            }
            return new RawKeyBytes(ki.toByteArray(), chainCode);
        }


        static List<ChildNumber> append(List<ChildNumber> path, ChildNumber childNumber) {
            ArrayList<ChildNumber> ret = new ArrayList<>(path);
            ret.add(childNumber);
            return Collections.unmodifiableList(ret);
        }

        static List<ChildNumber> concat(List<ChildNumber> path, List<ChildNumber> path2) {
            ArrayList<ChildNumber> ret = new ArrayList<>(path);
            ret.addAll(path2);
            return Collections.unmodifiableList(ret);
        }

        static String formatPath(List<ChildNumber> path) {
            StringBuilder ret = new StringBuilder();
            ret.append("M");
            for (ChildNumber c : path) {
                ret.append(c).append("/");
            }
            ret.delete(ret.length() - 1, ret.length());
            return ret.toString();
        }


        static HDKey createMasterPrivKeyFromBytes(byte[] privKeyBytes, byte[] chainCode) throws HDWalletException {
            BigInteger priv = new BigInteger(1, privKeyBytes);
            if (priv.compareTo(curve.getN()) > 0) {
                throw new HDWalletException("Generated master key is invalid.");
            }
            if (priv.compareTo(new BigInteger("0")) == 0) {
                throw new HDWalletException("Generated master key is invalid.");
            }
            return new HDKey(new ArrayList<>(), chainCode, priv, null);
        }

        static List<ChildNumber> parsePath(String path) {
            String[] parsedNodes = path.replace("M", "").split("/");
            List<ChildNumber> nodes = new ArrayList();
            String[] var3 = parsedNodes;
            int var4 = parsedNodes.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                String n = var3[var5];
                n = n.replaceAll(" ", "");
                if (n.length() != 0) {
                    boolean isHard = n.endsWith("H");
                    if (isHard) {
                        n = n.substring(0, n.length() - 1);
                    }

                    int nodeNumber = Integer.parseInt(n);
                    nodes.add(new ChildNumber(nodeNumber, isHard));
                }
            }

            return nodes;
        }

        static HDKey createMasterPrivateKey(byte[] seed) throws HDWalletException {
            MiscUtil.checkArgument(seed.length > 8, "Seed is too short and could be brute forced");
            // Calculate I = HMAC-SHA512(key="Bitcoin seed", msg=S)
            byte[] i = HashUtil.hmacSha512(HashUtil.createHmacSha512Digest("Bitcoin seed".getBytes()), seed);
            // Split I into two 32-byte sequences, Il and Ir.
            // Use Il as master secret key, and Ir as master chain code.
            MiscUtil.checkState(i.length == 64, i.length);
            byte[] il = Arrays.copyOfRange(i, 0, 32);
            byte[] ir = Arrays.copyOfRange(i, 32, 64);
            Arrays.fill(i, (byte) 0);
            HDKey masterPrivKey = createMasterPrivKeyFromBytes(il, ir);
            Arrays.fill(il, (byte) 0);
            Arrays.fill(ir, (byte) 0);
            // Child deterministic keys will chain up to their parents to find the keys.
            masterPrivKey.setCreationTimeSeconds(System.currentTimeMillis() / 1000);
            return masterPrivKey;
        }

    }

    static class HashUtil {
        static byte[] sha256hash160(byte[] input) {
            byte[] sha256 = Hash.sha256(input);
            RIPEMD160Digest digest = new RIPEMD160Digest();
            digest.update(sha256, 0, sha256.length);
            byte[] out = new byte[20];
            digest.doFinal(out, 0);
            return out;
        }


        static HMac createHmacSha512Digest(byte[] key) {
            SHA512Digest digest = new SHA512Digest();
            HMac hMac = new HMac(digest);
            hMac.init(new KeyParameter(key));
            return hMac;
        }

        static byte[] hmacSha512(HMac hmacSha512, byte[] input) {
            hmacSha512.reset();
            hmacSha512.update(input, 0, input.length);
            byte[] out = new byte[64];
            hmacSha512.doFinal(out, 0);
            return out;
        }

    }

    static class MiscUtil {
        static void checkArgument(boolean expression, Object errorMessage) {
            if (!expression) {
                throw new IllegalArgumentException(String.valueOf(errorMessage));
            }
        }

        static <T> T checkNotNull(T reference) {
            if (reference == null) {
                throw new NullPointerException();
            }
            return reference;
        }

        static void checkState(boolean expression, Object errorMessage) {
            if (!expression) {
                throw new IllegalStateException(String.valueOf(errorMessage));
            }
        }

        static void checkState(boolean expression) {
            if (!expression) {
                throw new IllegalStateException();
            }
        }

    }

    public static class HDWalletException extends RuntimeException {
        public HDWalletException() {
        }

        public HDWalletException(String message) {
            super(message);
        }
    }

    public static class HDSeed {
        private byte[] seed;
        private List<String> mnemonicCode;
        private long creationTimeSeconds;

        public HDSeed(byte[] seed, long creationTimeSeconds) {
            this.seed = seed;
            this.creationTimeSeconds = creationTimeSeconds;
        }

        public byte[] getSeed() {
            return seed;
        }

        public void setSeed(byte[] seed) {
            this.seed = seed;
        }

        public List<String> getMnemonicCode() {
            return mnemonicCode;
        }

        public void setMnemonicCode(List<String> mnemonicCode) {
            this.mnemonicCode = mnemonicCode;
        }

        public long getCreationTimeSeconds() {
            return creationTimeSeconds;
        }

        public void setCreationTimeSeconds(long creationTimeSeconds) {
            this.creationTimeSeconds = creationTimeSeconds;
        }
    }

    public static class ECKey {
        protected BigInteger priv;
        protected byte[] pub;
        protected byte[] pubKeyHash;
        protected boolean watching;

        public BigInteger getPriv() {
            return priv;
        }

        public byte[] getPub() {
            return pub;
        }

        public byte[] getPubKey() {
            return pub;
        }

        public byte[] getPubKeyHash() {
            if (pubKeyHash == null)
                pubKeyHash = HashUtil.sha256hash160(this.pub);
            return pubKeyHash;
        }

        public boolean isWatching() {
            return watching;
        }

        public void setWatching(boolean watching) {
            this.watching = watching;
        }
    }

    public static class HDKey extends ECKey {
        public static final Comparator<ECKey> CHILDNUM_ORDER = new Comparator<ECKey>() {
            public int compare(ECKey k1, ECKey k2) {
                ChildNumber cn1 = ((HDKey) k1).getChildNumber();
                ChildNumber cn2 = ((HDKey) k2).getChildNumber();
                return cn1.compareTo(cn2);
            }
        };
        private final HDKey parent;
        private final List<ChildNumber> childNumberPath;
        private final int depth;
        private final int parentFingerprint;
        private final byte[] chainCode;
        private long creationTimeSeconds;

        public HDKey(HDKey parent, List<ChildNumber> childNumberPath, int depth, int parentFingerprint, byte[] chainCode, BigInteger priv, byte[] pub) {
            this.parent = parent;
            this.childNumberPath = childNumberPath;
            this.depth = depth;
            this.chainCode = chainCode;
            this.parentFingerprint = parentFingerprint;
            this.priv = priv;
            this.pub = pub;
        }

        public HDKey(List<ChildNumber> childNumberPath, byte[] chainCode, BigInteger priv, HDKey parent) {
            MiscUtil.checkArgument(chainCode.length == 32, "");
            this.parent = parent;
            this.childNumberPath = childNumberPath;
            this.chainCode = Arrays.copyOf(chainCode, chainCode.length);
            this.depth = parent == null ? 0 : parent.depth + 1;
            this.parentFingerprint = (parent != null) ? parent.getFingerprint() : 0;
            this.priv = priv;
            this.pub = curve.getG().multiply(priv).getEncoded(true);
        }

        public HDKey getParent() {
            return parent;
        }

        public List<ChildNumber> getChildNumberPath() {
            return childNumberPath;
        }

        public int getDepth() {
            return depth;
        }

        public int getParentFingerprint() {
            return parentFingerprint;
        }

        public byte[] getChainCode() {
            return chainCode;
        }


        public List<ChildNumber> getPath() {
            return childNumberPath;
        }

        /**
         * Returns RIPE-MD160(SHA256(pub key bytes)).
         */
        public byte[] getIdentifier() {
            return HashUtil.sha256hash160(pub);
        }

        /**
         * Returns the first 32 bits of the result of {@link #getIdentifier()}.
         */
        public int getFingerprint() {
            // TODO: why is this different than armory's fingerprint? BIP 32: "The first 32 bits of the identifier are called the fingerprint."
            return ByteBuffer.wrap(Arrays.copyOfRange(getIdentifier(), 0, 4)).getInt();
        }

        public long getCreationTimeSeconds() {
            return creationTimeSeconds;
        }

        public void setCreationTimeSeconds(long creationTimeSeconds) {
            this.creationTimeSeconds = creationTimeSeconds;
        }

        public byte[] getPrivKeyBytes33() {
            byte[] bytes33 = new byte[33];
            byte[] priv = getPriv().toByteArray();
            System.arraycopy(priv, 0, bytes33, 33 - priv.length, priv.length);
            return bytes33;
        }

        public ChildNumber getChildNumber() {
            return childNumberPath.size() == 0 ? ChildNumber.ZERO : childNumberPath.get(childNumberPath.size() - 1);
        }

    }

    public static class ChildNumber implements Comparable<ChildNumber> {
        public static final int HARDENED_BIT = -2147483648;
        public static final ChildNumber ZERO = new ChildNumber(0);
        public static final ChildNumber ONE = new ChildNumber(1);
        public static final ChildNumber ZERO_HARDENED = new ChildNumber(0, true);
        private final int i;

        public ChildNumber(int childNumber, boolean isHardened) {
            if (hasHardenedBit(childNumber)) {
                throw new IllegalArgumentException("Most significant bit is reserved and shouldn't be set: " + childNumber);
            } else {
                this.i = isHardened ? childNumber | -2147483648 : childNumber;
            }
        }

        public ChildNumber(int i) {
            this.i = i;
        }

        private static boolean hasHardenedBit(int a) {
            return (a & -2147483648) != 0;
        }

        public int getI() {
            return this.i;
        }

        public int i() {
            return this.i;
        }

        public boolean isHardened() {
            return hasHardenedBit(this.i);
        }

        public int num() {
            return this.i & 2147483647;
        }

        public String toString() {
            return String.format(Locale.US, "%d%s", this.num(), this.isHardened() ? "H" : "");
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (o != null && this.getClass() == o.getClass()) {
                return this.i == ((ChildNumber) o).i;
            } else {
                return false;
            }
        }

        public int hashCode() {
            return this.i;
        }

        public int compareTo(ChildNumber other) {
            return new Integer(this.num()).compareTo(new Integer(other.num()));
        }
    }

    public static class Hierarchy {
        private final Map<List<ChildNumber>, HDKey> keys = new HashMap<>();
        private final List<ChildNumber> rootPath;
        // Keep track of how many child keys each node has. This is kind of weak.
        private final Map<List<ChildNumber>, ChildNumber> lastChildNumbers = new HashMap();

        /**
         * Constructs a new hierarchy rooted at the given key. Note that this does not have to be the top of the tree.
         * You can construct a DeterministicHierarchy for a subtree of a larger tree that you may not own.
         */
        public Hierarchy(HDKey rootKey) {
            putKey(rootKey);
            rootPath = rootKey.getPath();
        }

        public final void putKey(HDKey key) {
            List<ChildNumber> path = key.getPath();
            // Update our tracking of what the next child in each branch of the tree should be. Just assume that keys are
            // inserted in order here.
            final HDKey parent = key.getParent();
            if (parent != null)
                lastChildNumbers.put(parent.getPath(), key.getChildNumber());
            keys.put(path, key);
        }

        /**
         * Returns a key for the given path, optionally creating it.
         *
         * @param path         the path to the key
         * @param relativePath whether the path is relative to the root path
         * @param create       whether the key corresponding to path should be created (with any necessary ancestors) if it doesn't exist already
         * @return next newly created key using the child derivation function
         * @throws IllegalArgumentException if create is false and the path was not found.
         */
        public HDKey get(List<ChildNumber> path, boolean relativePath, boolean create) {

            List<ChildNumber> absolutePath = new ArrayList<>();
            if (relativePath)
                absolutePath.addAll(rootPath);
            absolutePath.addAll(path);
            if (!keys.containsKey(absolutePath)) {
                if (!create)
                    throw new IllegalArgumentException(String.format(Locale.US, "No key found for %s path %s.",
                            relativePath ? "relative" : "absolute", HDUtil.formatPath(path)));
                MiscUtil.checkArgument(absolutePath.size() > 0, "Can't derive the master key: nothing to derive from.");
                HDKey parent = get(absolutePath.subList(0, absolutePath.size() - 1), false, true);
                putKey(HDUtil.deriveChildKey(parent, absolutePath.get(absolutePath.size() - 1)));
            }
            return keys.get(absolutePath);
        }

        /**
         * Extends the tree by calculating the next key that hangs off the given parent path. For example, if you pass a
         * path of 1/2 here and there are already keys 1/2/1 and 1/2/2 then it will derive 1/2/3.
         *
         * @param parentPath        the path to the parent
         * @param relative          whether the path is relative to the root path
         * @param createParent      whether the parent corresponding to path should be created (with any necessary ancestors) if it doesn't exist already
         * @param privateDerivation whether to use private or public derivation
         * @return next newly created key using the child derivation funtcion
         * @throws IllegalArgumentException if the parent doesn't exist and createParent is false.
         */
        public HDKey deriveNextChild(List<ChildNumber> parentPath, boolean relative, boolean createParent, boolean privateDerivation) {
            HDKey parent = get(parentPath, relative, createParent);
            int nAttempts = 0;
            while (nAttempts++ < MAX_CHILD_DERIVATION_ATTEMPTS) {
                try {
                    ChildNumber createChildNumber = getNextChildNumberToDerive(parent.getPath(), privateDerivation);
                    return deriveChild(parent, createChildNumber);
                } catch (HDWalletException ignore) {
                }
            }
            throw new HDWalletException("Maximum number of child derivation attempts reached, this is probably an indication of a bug.");
        }

        private ChildNumber getNextChildNumberToDerive(List<ChildNumber> path, boolean privateDerivation) {
            ChildNumber lastChildNumber = lastChildNumbers.get(path);
            ChildNumber nextChildNumber = new ChildNumber(lastChildNumber != null ? lastChildNumber.num() + 1 : 0, privateDerivation);
            lastChildNumbers.put(path, nextChildNumber);
            return nextChildNumber;
        }

        public int getNumChildren(List<ChildNumber> path) {
            final ChildNumber cn = lastChildNumbers.get(path);
            if (cn == null)
                return 0;
            else
                return cn.num() + 1;   // children start with zero based childnumbers
        }

        /**
         * Extends the tree by calculating the requested child for the given path. For example, to get the key at position
         * 1/2/3 you would pass 1/2 as the parent path and 3 as the child number.
         *
         * @param parentPath   the path to the parent
         * @param relative     whether the path is relative to the root path
         * @param createParent whether the parent corresponding to path should be created (with any necessary ancestors) if it doesn't exist already
         * @return the requested key.
         * @throws IllegalArgumentException if the parent doesn't exist and createParent is false.
         */
        public HDKey deriveChild(List<ChildNumber> parentPath, boolean relative, boolean createParent, ChildNumber createChildNumber) {
            return deriveChild(get(parentPath, relative, createParent), createChildNumber);
        }

        private HDKey deriveChild(HDKey parent, ChildNumber createChildNumber) {
            HDKey childKey = HDUtil.deriveChildKey(parent, createChildNumber);
            putKey(childKey);
            return childKey;
        }

        /**
         * Returns the root key that the {@link Hierarchy} was created with.
         */
        public HDKey getRootKey() {
            return get(rootPath, false, false);
        }


    }

    public static class RawKeyBytes {
        public final byte[] keyBytes;
        public final byte[] chainCode;

        public RawKeyBytes(byte[] keyBytes, byte[] chainCode) {
            this.keyBytes = keyBytes;
            this.chainCode = chainCode;
        }
    }

    public static class BasicKeyChain {
        private final ReentrantLock lock = new ReentrantLock();

        // Maps used to let us quickly look up a key given data we find in transcations or the block chain.
        private LinkedHashMap<ByteString, ECKey> hashToKeys;
        private LinkedHashMap<ByteString, ECKey> pubkeyToKeys;
        private boolean isWatching;

        public BasicKeyChain() {
            hashToKeys = new LinkedHashMap<>();
            pubkeyToKeys = new LinkedHashMap<>();
        }

        public int importKeys(ECKey... keys) {

            return importKeys(Arrays.asList(keys));
        }

        public int importKeys(List<? extends ECKey> keys) {
            lock.lock();
            try {
                List<ECKey> actuallyAdded = new ArrayList<>(keys.size());
                for (final ECKey key : keys) {
                    if (hasKey(key)) continue;
                    actuallyAdded.add(key);
                    importKeyLocked(key);
                }
                return actuallyAdded.size();
            } finally {
                lock.unlock();
            }
        }


        private void importKeyLocked(ECKey key) {
            if (hashToKeys.isEmpty()) {
                isWatching = key.isWatching();
            } else {
                if (key.isWatching() && !isWatching)
                    throw new IllegalArgumentException("Key is watching but chain is not");
                if (!key.isWatching() && isWatching)
                    throw new IllegalArgumentException("Key is not watching but chain is");
            }
            ECKey previousKey = pubkeyToKeys.put(ByteString.copyFrom(key.getPubKey()), key);
            hashToKeys.put(ByteString.copyFrom(key.getPubKeyHash()), key);
            MiscUtil.checkState(previousKey == null);
        }


        /**
         * Imports a key to the key chain. If key is present in the key chain, ignore it.
         */
        public void importKey(ECKey key) {
            lock.lock();
            try {
                if (hasKey(key)) return;
                importKeyLocked(key);
            } finally {
                lock.unlock();
            }
        }

        public ECKey findKeyFromPubHash(byte[] pubkeyHash) {
            lock.lock();
            try {
                return hashToKeys.get(ByteString.copyFrom(pubkeyHash));
            } finally {
                lock.unlock();
            }
        }

        public ECKey findKeyFromPubKey(byte[] pubkey) {
            lock.lock();
            try {
                return pubkeyToKeys.get(ByteString.copyFrom(pubkey));
            } finally {
                lock.unlock();
            }
        }


        public boolean hasKey(ECKey key) {
            return findKeyFromPubKey(key.getPubKey()) != null;
        }


        public int numKeys() {
            return pubkeyToKeys.size();
        }

    }


    public static class HDKeyChain {

        public static final List<ChildNumber> ACCOUNT_ZERO_PATH = Arrays.asList(ChildNumber.ZERO_HARDENED);
        public static final List<ChildNumber> EXTERNAL_SUBPATH = Arrays.asList(ChildNumber.ZERO);
        public static final List<ChildNumber> INTERNAL_SUBPATH = Arrays.asList(ChildNumber.ONE);
        public static final List<ChildNumber> EXTERNAL_PATH = HDUtil.concat(ACCOUNT_ZERO_PATH, EXTERNAL_SUBPATH);
        public static final List<ChildNumber> INTERNAL_PATH = HDUtil.concat(ACCOUNT_ZERO_PATH, INTERNAL_SUBPATH);
        // m / 44' / 0' / 0'
        public static final List<ChildNumber> BIP44_ACCOUNT_ZERO_PATH =
                Arrays.asList(new ChildNumber(44, true), ChildNumber.ZERO_HARDENED, ChildNumber.ZERO_HARDENED);
        private Hierarchy hierarchy;
        private HDKey rootKey;
        private HDSeed seed;
        private BasicKeyChain basicKeyChain;
        private HDKey externalParentKey, internalParentKey;


        public HDKeyChain(HDSeed seed) {
            this.seed = seed;
            basicKeyChain = new BasicKeyChain();
            rootKey = HDUtil.createMasterPrivateKey(MiscUtil.checkNotNull(seed.getSeed()));
            rootKey.setCreationTimeSeconds(seed.getCreationTimeSeconds());

            addToBasicChain(rootKey);
            hierarchy = new Hierarchy(rootKey);
            for (int i = 1; i <= getAccountPath().size(); i++) {
                addToBasicChain(hierarchy.get(getAccountPath().subList(0, i), false, true));
            }
            initializeHierarchyUnencrypted(rootKey);
        }

        private void initializeHierarchyUnencrypted(HDKey baseKey) {
            externalParentKey = hierarchy.deriveChild(getAccountPath(), false, false, ChildNumber.ZERO);
            internalParentKey = hierarchy.deriveChild(getAccountPath(), false, false, ChildNumber.ONE);
            addToBasicChain(externalParentKey);
            addToBasicChain(internalParentKey);
        }

        private void addToBasicChain(HDKey key) {
            basicKeyChain.importKeys(Arrays.asList(key));
        }

        protected List<ChildNumber> getAccountPath() {
            return ACCOUNT_ZERO_PATH;
        }

        public HDKey getKeyByPath(List<ChildNumber> path, boolean create) {
            return hierarchy.get(path, false, create);
        }

        private ChildNumber getNextChildNumber(List<ChildNumber> path, boolean privateDerivation) {
            return hierarchy.getNextChildNumberToDerive(path, privateDerivation);
        }

        public int countChildren(List<ChildNumber> path) {
            return hierarchy.getNumChildren(path);
        }


        private HDKey deriveChild(HDKey parent, int index, boolean hardened) {
            return hierarchy.deriveChild(parent, new ChildNumber(index, hardened));
        }

    }


}
