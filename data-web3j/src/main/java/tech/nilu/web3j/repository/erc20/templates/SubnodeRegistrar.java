package tech.nilu.web3j.repository.erc20.templates;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
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
public class SubnodeRegistrar extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_SETSUBNODEOWNER = "setSubnodeOwner";

    public static final String FUNC_SUBNODEOWNER = "subnodeOwner";

    public static final String FUNC_SETRESOLVER = "setResolver";

    @Deprecated
    protected SubnodeRegistrar(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SubnodeRegistrar(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected SubnodeRegistrar(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SubnodeRegistrar(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    @Deprecated
    public static SubnodeRegistrar load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SubnodeRegistrar(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SubnodeRegistrar load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SubnodeRegistrar(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static SubnodeRegistrar load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SubnodeRegistrar(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SubnodeRegistrar load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SubnodeRegistrar(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<SubnodeRegistrar> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String nnsAddr, String resolverAddr) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.asList(new org.web3j.abi.datatypes.Address(160, nnsAddr),
                new org.web3j.abi.datatypes.Address(160, resolverAddr)));
        return deployRemoteCall(SubnodeRegistrar.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<SubnodeRegistrar> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String nnsAddr, String resolverAddr) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.asList(new org.web3j.abi.datatypes.Address(160, nnsAddr),
                new org.web3j.abi.datatypes.Address(160, resolverAddr)));
        return deployRemoteCall(SubnodeRegistrar.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SubnodeRegistrar> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String nnsAddr, String resolverAddr) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.asList(new org.web3j.abi.datatypes.Address(160, nnsAddr),
                new org.web3j.abi.datatypes.Address(160, resolverAddr)));
        return deployRemoteCall(SubnodeRegistrar.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<SubnodeRegistrar> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String nnsAddr, String resolverAddr) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.asList(new org.web3j.abi.datatypes.Address(160, nnsAddr),
                new org.web3j.abi.datatypes.Address(160, resolverAddr)));
        return deployRemoteCall(SubnodeRegistrar.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public RemoteFunctionCall<TransactionReceipt> setSubnodeOwner(byte[] node, byte[] label, String owner) {
        final Function function = new Function(
                FUNC_SETSUBNODEOWNER,
                Arrays.asList(new org.web3j.abi.datatypes.generated.Bytes32(node),
                        new org.web3j.abi.datatypes.generated.Bytes32(label),
                        new org.web3j.abi.datatypes.Address(160, owner)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> subnodeOwner() {
        final Function function = new Function(FUNC_SUBNODEOWNER,
                Arrays.asList(),
                Arrays.asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setResolver(byte[] node) {
        final Function function = new Function(
                FUNC_SETRESOLVER,
                Arrays.asList(new org.web3j.abi.datatypes.generated.Bytes32(node)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }
}
