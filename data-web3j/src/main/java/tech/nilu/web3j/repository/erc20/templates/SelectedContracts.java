package tech.nilu.web3j.repository.erc20.templates;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

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
public class SelectedContracts extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_INFOOF = "infoOf";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_ADDCONTRACT = "addContract";

    public static final String FUNC_REMOVECONTRACT = "removeContract";

    public static final String FUNC_LISTCONTRACTS = "listContracts";

    @Deprecated
    protected SelectedContracts(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SelectedContracts(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected SelectedContracts(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SelectedContracts(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    @Deprecated
    public static SelectedContracts load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SelectedContracts(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SelectedContracts load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SelectedContracts(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static SelectedContracts load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SelectedContracts(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SelectedContracts load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SelectedContracts(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<SelectedContracts> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SelectedContracts.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<SelectedContracts> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SelectedContracts.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SelectedContracts> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SelectedContracts.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SelectedContracts> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SelectedContracts.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public RemoteFunctionCall<Tuple4<String, String, String, String>> infoOf(String c) {
        final Function function = new Function(FUNC_INFOOF,
                Arrays.asList(new org.web3j.abi.datatypes.Address(160, c)),
                Arrays.asList(new TypeReference<Address>() {
                }, new TypeReference<Utf8String>() {
                }, new TypeReference<Utf8String>() {
                }, new TypeReference<Utf8String>() {
                }));
        return new RemoteFunctionCall<Tuple4<String, String, String, String>>(function,
                new Callable<Tuple4<String, String, String, String>>() {
                    @Override
                    public Tuple4<String, String, String, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<String, String, String, String>(
                                (String) results.get(0).getValue(),
                                (String) results.get(1).getValue(),
                                (String) results.get(2).getValue(),
                                (String) results.get(3).getValue());
                    }
                });
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(FUNC_OWNER,
                Arrays.asList(),
                Arrays.asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> addContract(String c, String name, String url, String types) {
        final Function function = new Function(
                FUNC_ADDCONTRACT,
                Arrays.asList(new org.web3j.abi.datatypes.Address(160, c),
                        new org.web3j.abi.datatypes.Utf8String(name),
                        new org.web3j.abi.datatypes.Utf8String(url),
                        new org.web3j.abi.datatypes.Utf8String(types)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> removeContract(String c) {
        final Function function = new Function(
                FUNC_REMOVECONTRACT,
                Arrays.asList(new org.web3j.abi.datatypes.Address(160, c)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> listContracts() {
        final Function function = new Function(FUNC_LISTCONTRACTS,
                Arrays.asList(),
                Arrays.asList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }
}
