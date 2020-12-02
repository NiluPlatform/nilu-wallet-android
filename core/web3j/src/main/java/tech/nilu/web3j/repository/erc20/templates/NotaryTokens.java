package tech.nilu.web3j.repository.erc20.templates;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.7.0.
 */
@SuppressWarnings("rawtypes")
public class NotaryTokens extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_ADDTOKEN = "addToken";

    public static final String FUNC_COUNT = "count";

    public static final String FUNC_COUNTTOKENSOF = "countTokensOf";

    public static final String FUNC_GETTOKEN = "getToken";

    public static final String FUNC_GETTOKENS = "getTokens";

    public static final String FUNC_REMOVETOKEN = "removeToken";

    public static final String FUNC_TOKENSOF = "tokensOf";

    @Deprecated
    protected NotaryTokens(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected NotaryTokens(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected NotaryTokens(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected NotaryTokens(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    @Deprecated
    public static NotaryTokens load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new NotaryTokens(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static NotaryTokens load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new NotaryTokens(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static NotaryTokens load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new NotaryTokens(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static NotaryTokens load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new NotaryTokens(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<NotaryTokens> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(NotaryTokens.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<NotaryTokens> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(NotaryTokens.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<NotaryTokens> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(NotaryTokens.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<NotaryTokens> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(NotaryTokens.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public RemoteFunctionCall<TransactionReceipt> addToken(String creator, String tokenAddr, String name, String symbol, BigInteger decimals, BigInteger rate, BigInteger supply) {
        final Function function = new Function(
                FUNC_ADDTOKEN,
                Arrays.asList(new org.web3j.abi.datatypes.Address(160, creator),
                        new org.web3j.abi.datatypes.Address(160, tokenAddr),
                        new org.web3j.abi.datatypes.Utf8String(name),
                        new org.web3j.abi.datatypes.Utf8String(symbol),
                        new org.web3j.abi.datatypes.generated.Uint8(decimals),
                        new org.web3j.abi.datatypes.generated.Uint256(rate),
                        new org.web3j.abi.datatypes.generated.Uint256(supply)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> count() {
        final Function function = new Function(FUNC_COUNT,
                Arrays.asList(),
                Arrays.asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> countTokensOf(String addr) {
        final Function function = new Function(FUNC_COUNTTOKENSOF,
                Arrays.asList(new org.web3j.abi.datatypes.Address(160, addr)),
                Arrays.asList(new TypeReference<Uint256>() {
                }));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> getToken(String addr) {
        final Function function = new Function(FUNC_GETTOKEN,
                Arrays.asList(new org.web3j.abi.datatypes.Address(160, addr)),
                Arrays.asList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getTokens(BigInteger from, BigInteger size) {
        final Function function = new Function(FUNC_GETTOKENS,
                Arrays.asList(new org.web3j.abi.datatypes.generated.Uint256(from),
                        new org.web3j.abi.datatypes.generated.Uint256(size)),
                Arrays.asList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> removeToken(String tokenAddr) {
        final Function function = new Function(
                FUNC_REMOVETOKEN,
                Arrays.asList(new org.web3j.abi.datatypes.Address(160, tokenAddr)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> tokensOf(String addr) {
        final Function function = new Function(FUNC_TOKENSOF,
                Arrays.asList(new org.web3j.abi.datatypes.Address(160, addr)),
                Arrays.asList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }
}
