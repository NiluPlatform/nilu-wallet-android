package tech.nilu.web3j.repository.erc20.templates;

import org.jetbrains.annotations.NotNull;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;

public class ERC20BasicImpl extends Contract implements ERC20Basic {

    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_TOTAL_SUPPLY = "totalSupply";

    public static final String FUNC_RATE = "rate";

    public static final String FUNC_DECIMALS = "decimals";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_BALANCE_OF = "balanceOf";

    public static final String FUNC_SEND_TRANSACTION = "sendTransaction";

    public static final String FUNC_IS_PAYABLE = "isPayable";

    public static final String FUNC_CREATE_TOKENS = "createTokens";

    public static final String FUNC_TRANSFER = "transfer";

    public static final Event TRANSFER_EVENT = new Event(
            "Transfer",
            Arrays.asList(
                    new TypeReference<Address>(true) {
                    }, new TypeReference<Address>(true) {
                    }, new TypeReference<Uint256>() {
                    }
            )
    );

    @Deprecated
    protected ERC20BasicImpl(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ERC20BasicImpl(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ERC20BasicImpl(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ERC20BasicImpl(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    @Deprecated
    public static ERC20BasicImpl load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ERC20BasicImpl(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ERC20BasicImpl load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ERC20BasicImpl(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ERC20BasicImpl load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ERC20BasicImpl(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ERC20BasicImpl load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ERC20BasicImpl(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    @NotNull
    @Override
    public RemoteCall<String> name() {
        final Function function = new Function(
                FUNC_NAME,
                Arrays.asList(),
                Arrays.asList(new TypeReference<Utf8String>() {
                })
        );
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @NotNull
    @Override
    public RemoteCall<BigInteger> totalSupply() {
        final Function function = new Function(
                FUNC_TOTAL_SUPPLY,
                Arrays.asList(),
                Arrays.asList(new TypeReference<Uint256>() {
                })
        );
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @NotNull
    @Override
    public RemoteCall<BigInteger> rate() {
        final Function function = new Function(
                FUNC_RATE,
                Arrays.asList(),
                Arrays.asList(new TypeReference<Uint256>() {
                })
        );
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @NotNull
    @Override
    public RemoteCall<BigInteger> decimals() {
        final Function function = new Function(
                FUNC_DECIMALS,
                Arrays.asList(),
                Arrays.asList(new TypeReference<Uint8>() {
                })
        );
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @NotNull
    @Override
    public RemoteCall<String> symbol() {
        final Function function = new Function(
                FUNC_SYMBOL,
                Arrays.asList(),
                Arrays.asList(new TypeReference<Utf8String>() {
                })
        );
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @NotNull
    @Override
    public RemoteCall<Boolean> isPayable() {
        final Function function = new Function(
                FUNC_IS_PAYABLE,
                Arrays.asList(),
                Arrays.asList(new TypeReference<Bool>() {
                })
        );
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    @NotNull
    @Override
    public RemoteCall<BigInteger> balanceOf(@NotNull String me) {
        final Function function = new Function(
                FUNC_BALANCE_OF,
                Arrays.asList(new Address(160, me)),
                Arrays.asList(new TypeReference<Uint256>() {
                })
        );
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    @NotNull
    @Override
    public RemoteCall<TransactionReceipt> transfer(@NotNull String to, @NotNull BigInteger value) {
        final Function function = new Function(
                FUNC_SEND_TRANSACTION,
                Arrays.asList(new Address(160, to), new Uint256(value)),
                Collections.emptyList()
        );
        return executeRemoteCallTransaction(function);
    }

    @NotNull
    @Override
    public List<TransferEvent.Response> getTransferEvents(@NotNull TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEvent.Response> responses = new ArrayList<>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEvent.Response typedResponse = new TransferEvent.Response(
                    eventValues.getLog(),
                    (String) eventValues.getIndexedValues().get(0).getValue(),
                    (String) eventValues.getIndexedValues().get(1).getValue(),
                    (BigInteger) eventValues.getNonIndexedValues().get(0).getValue()
            );
            responses.add(typedResponse);
        }
        return responses;
    }

    @NotNull
    @Override
    public Flowable<TransferEvent.Response> transferEventFlowable(@NotNull DefaultBlockParameter startBlock, @NotNull DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventFlowable(filter);
    }

    public Flowable<TransferEvent.Response> transferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> {
            EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);
            return new TransferEvent.Response(
                    log,
                    (String) eventValues.getIndexedValues().get(0).getValue(),
                    (String) eventValues.getIndexedValues().get(1).getValue(),
                    (BigInteger) eventValues.getNonIndexedValues().get(0).getValue()
            );
        });
    }

    @NotNull
    @Override
    public RemoteCall<TransactionReceipt> createTokens(@NotNull BigInteger weiValue) {
        final Function function = new Function(
                FUNC_CREATE_TOKENS,
                Arrays.asList(),
                Collections.emptyList()
        );
        return executeRemoteCallTransaction(function, weiValue);
    }

    @NotNull
    @Override
    public String prepareTransferData(@NotNull String to, @NotNull BigInteger value) {
        final Function function = new Function(
                FUNC_TRANSFER,
                Arrays.asList(new Address(160, to), new Uint256(value)),
                Collections.emptyList()
        );
        return FunctionEncoder.encode(function);
    }
}
