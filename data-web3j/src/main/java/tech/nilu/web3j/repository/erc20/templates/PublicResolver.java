package tech.nilu.web3j.repository.erc20.templates;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Flowable;

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
public class PublicResolver extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final String FUNC_SETTEXT = "setText";

    public static final String FUNC_ABI = "ABI";

    public static final String FUNC_SETPUBKEY = "setPubkey";

    public static final String FUNC_CONTENT = "content";

    public static final String FUNC_ADDR = "addr";

    public static final String FUNC_TEXT = "text";

    public static final String FUNC_SETABI = "setABI";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_SETNAME = "setName";

    public static final String FUNC_SETMULTIHASH = "setMultihash";

    public static final String FUNC_SETCONTENT = "setContent";

    public static final String FUNC_PUBKEY = "pubkey";

    public static final String FUNC_SETADDR = "setAddr";

    public static final String FUNC_MULTIHASH = "multihash";

    public static final Event ADDRCHANGED_EVENT = new Event("AddrChanged",
            Arrays.asList(new TypeReference<Bytes32>(true) {
            }, new TypeReference<Address>() {
            }));

    public static final Event CONTENTCHANGED_EVENT = new Event("ContentChanged",
            Arrays.asList(new TypeReference<Bytes32>(true) {
            }, new TypeReference<Bytes32>() {
            }));

    public static final Event NAMECHANGED_EVENT = new Event("NameChanged",
            Arrays.asList(new TypeReference<Bytes32>(true) {
            }, new TypeReference<Utf8String>() {
            }));

    public static final Event ABICHANGED_EVENT = new Event("ABIChanged",
            Arrays.asList(new TypeReference<Bytes32>(true) {
            }, new TypeReference<Uint256>(true) {
            }));

    public static final Event PUBKEYCHANGED_EVENT = new Event("PubkeyChanged",
            Arrays.asList(new TypeReference<Bytes32>(true) {
            }, new TypeReference<Bytes32>() {
            }, new TypeReference<Bytes32>() {
            }));

    public static final Event TEXTCHANGED_EVENT = new Event("TextChanged",
            Arrays.asList(new TypeReference<Bytes32>(true) {
            }, new TypeReference<Utf8String>() {
            }, new TypeReference<Utf8String>() {
            }));

    public static final Event MULTIHASHCHANGED_EVENT = new Event("MultihashChanged",
            Arrays.asList(new TypeReference<Bytes32>(true) {
            }, new TypeReference<DynamicBytes>() {
            }));

    @Deprecated
    protected PublicResolver(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PublicResolver(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected PublicResolver(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected PublicResolver(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    @Deprecated
    public static PublicResolver load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new PublicResolver(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static PublicResolver load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PublicResolver(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static PublicResolver load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new PublicResolver(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static PublicResolver load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new PublicResolver(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<PublicResolver> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String nnsAddr) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.asList(new org.web3j.abi.datatypes.Address(160, nnsAddr)));
        return deployRemoteCall(PublicResolver.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<PublicResolver> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String nnsAddr) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.asList(new org.web3j.abi.datatypes.Address(160, nnsAddr)));
        return deployRemoteCall(PublicResolver.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<PublicResolver> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String nnsAddr) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.asList(new org.web3j.abi.datatypes.Address(160, nnsAddr)));
        return deployRemoteCall(PublicResolver.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<PublicResolver> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String nnsAddr) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.asList(new org.web3j.abi.datatypes.Address(160, nnsAddr)));
        return deployRemoteCall(PublicResolver.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public RemoteFunctionCall<Boolean> supportsInterface(byte[] interfaceID) {
        final Function function = new Function(FUNC_SUPPORTSINTERFACE,
                Arrays.asList(new org.web3j.abi.datatypes.generated.Bytes4(interfaceID)),
                Arrays.asList(new TypeReference<Bool>() {
                }));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setText(byte[] node, String key, String value) {
        final Function function = new Function(
                FUNC_SETTEXT,
                Arrays.asList(new org.web3j.abi.datatypes.generated.Bytes32(node),
                        new org.web3j.abi.datatypes.Utf8String(key),
                        new org.web3j.abi.datatypes.Utf8String(value)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple2<BigInteger, byte[]>> ABI(byte[] node, BigInteger contentTypes) {
        final Function function = new Function(FUNC_ABI,
                Arrays.asList(new org.web3j.abi.datatypes.generated.Bytes32(node),
                        new org.web3j.abi.datatypes.generated.Uint256(contentTypes)),
                Arrays.asList(new TypeReference<Uint256>() {
                }, new TypeReference<DynamicBytes>() {
                }));
        return new RemoteFunctionCall<Tuple2<BigInteger, byte[]>>(function,
                new Callable<Tuple2<BigInteger, byte[]>>() {
                    @Override
                    public Tuple2<BigInteger, byte[]> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<BigInteger, byte[]>(
                                (BigInteger) results.get(0).getValue(),
                                (byte[]) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> setPubkey(byte[] node, byte[] x, byte[] y) {
        final Function function = new Function(
                FUNC_SETPUBKEY,
                Arrays.asList(new org.web3j.abi.datatypes.generated.Bytes32(node),
                        new org.web3j.abi.datatypes.generated.Bytes32(x),
                        new org.web3j.abi.datatypes.generated.Bytes32(y)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<byte[]> content(byte[] node) {
        final Function function = new Function(FUNC_CONTENT,
                Arrays.asList(new org.web3j.abi.datatypes.generated.Bytes32(node)),
                Arrays.asList(new TypeReference<Bytes32>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<String> addr(byte[] node) {
        final Function function = new Function(FUNC_ADDR,
                Arrays.asList(new org.web3j.abi.datatypes.generated.Bytes32(node)),
                Arrays.asList(new TypeReference<Address>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> text(byte[] node, String key) {
        final Function function = new Function(FUNC_TEXT,
                Arrays.asList(new org.web3j.abi.datatypes.generated.Bytes32(node),
                        new org.web3j.abi.datatypes.Utf8String(key)),
                Arrays.asList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setABI(byte[] node, BigInteger contentType, byte[] data) {
        final Function function = new Function(
                FUNC_SETABI,
                Arrays.asList(new org.web3j.abi.datatypes.generated.Bytes32(node),
                        new org.web3j.abi.datatypes.generated.Uint256(contentType),
                        new org.web3j.abi.datatypes.DynamicBytes(data)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> name(byte[] node) {
        final Function function = new Function(FUNC_NAME,
                Arrays.asList(new org.web3j.abi.datatypes.generated.Bytes32(node)),
                Arrays.asList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setName(byte[] node, String name) {
        final Function function = new Function(
                FUNC_SETNAME,
                Arrays.asList(new org.web3j.abi.datatypes.generated.Bytes32(node),
                        new org.web3j.abi.datatypes.Utf8String(name)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setMultihash(byte[] node, byte[] hash) {
        final Function function = new Function(
                FUNC_SETMULTIHASH,
                Arrays.asList(new org.web3j.abi.datatypes.generated.Bytes32(node),
                        new org.web3j.abi.datatypes.DynamicBytes(hash)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setContent(byte[] node, byte[] hash) {
        final Function function = new Function(
                FUNC_SETCONTENT,
                Arrays.asList(new org.web3j.abi.datatypes.generated.Bytes32(node),
                        new org.web3j.abi.datatypes.generated.Bytes32(hash)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple2<byte[], byte[]>> pubkey(byte[] node) {
        final Function function = new Function(FUNC_PUBKEY,
                Arrays.asList(new org.web3j.abi.datatypes.generated.Bytes32(node)),
                Arrays.asList(new TypeReference<Bytes32>() {
                }, new TypeReference<Bytes32>() {
                }));
        return new RemoteFunctionCall<Tuple2<byte[], byte[]>>(function,
                new Callable<Tuple2<byte[], byte[]>>() {
                    @Override
                    public Tuple2<byte[], byte[]> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<byte[], byte[]>(
                                (byte[]) results.get(0).getValue(),
                                (byte[]) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> setAddr(byte[] node, String addr) {
        final Function function = new Function(
                FUNC_SETADDR,
                Arrays.asList(new org.web3j.abi.datatypes.generated.Bytes32(node),
                        new org.web3j.abi.datatypes.Address(160, addr)),
                Collections.emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<byte[]> multihash(byte[] node) {
        final Function function = new Function(FUNC_MULTIHASH,
                Arrays.asList(new org.web3j.abi.datatypes.generated.Bytes32(node)),
                Arrays.asList(new TypeReference<DynamicBytes>() {
                }));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public List<AddrChangedEventResponse> getAddrChangedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ADDRCHANGED_EVENT, transactionReceipt);
        ArrayList<AddrChangedEventResponse> responses = new ArrayList<AddrChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AddrChangedEventResponse typedResponse = new AddrChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.a = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AddrChangedEventResponse> addrChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, AddrChangedEventResponse>() {
            @Override
            public AddrChangedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ADDRCHANGED_EVENT, log);
                AddrChangedEventResponse typedResponse = new AddrChangedEventResponse();
                typedResponse.log = log;
                typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.a = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AddrChangedEventResponse> addrChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ADDRCHANGED_EVENT));
        return addrChangedEventFlowable(filter);
    }

    public List<ContentChangedEventResponse> getContentChangedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CONTENTCHANGED_EVENT, transactionReceipt);
        ArrayList<ContentChangedEventResponse> responses = new ArrayList<ContentChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ContentChangedEventResponse typedResponse = new ContentChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.hash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ContentChangedEventResponse> contentChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ContentChangedEventResponse>() {
            @Override
            public ContentChangedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CONTENTCHANGED_EVENT, log);
                ContentChangedEventResponse typedResponse = new ContentChangedEventResponse();
                typedResponse.log = log;
                typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.hash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ContentChangedEventResponse> contentChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CONTENTCHANGED_EVENT));
        return contentChangedEventFlowable(filter);
    }

    public List<NameChangedEventResponse> getNameChangedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(NAMECHANGED_EVENT, transactionReceipt);
        ArrayList<NameChangedEventResponse> responses = new ArrayList<NameChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            NameChangedEventResponse typedResponse = new NameChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.name = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<NameChangedEventResponse> nameChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, NameChangedEventResponse>() {
            @Override
            public NameChangedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(NAMECHANGED_EVENT, log);
                NameChangedEventResponse typedResponse = new NameChangedEventResponse();
                typedResponse.log = log;
                typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.name = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<NameChangedEventResponse> nameChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(NAMECHANGED_EVENT));
        return nameChangedEventFlowable(filter);
    }

    public List<ABIChangedEventResponse> getABIChangedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ABICHANGED_EVENT, transactionReceipt);
        ArrayList<ABIChangedEventResponse> responses = new ArrayList<ABIChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ABIChangedEventResponse typedResponse = new ABIChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.contentType = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ABIChangedEventResponse> aBIChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ABIChangedEventResponse>() {
            @Override
            public ABIChangedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ABICHANGED_EVENT, log);
                ABIChangedEventResponse typedResponse = new ABIChangedEventResponse();
                typedResponse.log = log;
                typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.contentType = (BigInteger) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ABIChangedEventResponse> aBIChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ABICHANGED_EVENT));
        return aBIChangedEventFlowable(filter);
    }

    public List<PubkeyChangedEventResponse> getPubkeyChangedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PUBKEYCHANGED_EVENT, transactionReceipt);
        ArrayList<PubkeyChangedEventResponse> responses = new ArrayList<PubkeyChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PubkeyChangedEventResponse typedResponse = new PubkeyChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.x = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.y = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PubkeyChangedEventResponse> pubkeyChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, PubkeyChangedEventResponse>() {
            @Override
            public PubkeyChangedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PUBKEYCHANGED_EVENT, log);
                PubkeyChangedEventResponse typedResponse = new PubkeyChangedEventResponse();
                typedResponse.log = log;
                typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.x = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.y = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PubkeyChangedEventResponse> pubkeyChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PUBKEYCHANGED_EVENT));
        return pubkeyChangedEventFlowable(filter);
    }

    public List<TextChangedEventResponse> getTextChangedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TEXTCHANGED_EVENT, transactionReceipt);
        ArrayList<TextChangedEventResponse> responses = new ArrayList<TextChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TextChangedEventResponse typedResponse = new TextChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.indexedKey = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.key = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TextChangedEventResponse> textChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, TextChangedEventResponse>() {
            @Override
            public TextChangedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TEXTCHANGED_EVENT, log);
                TextChangedEventResponse typedResponse = new TextChangedEventResponse();
                typedResponse.log = log;
                typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.indexedKey = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.key = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TextChangedEventResponse> textChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TEXTCHANGED_EVENT));
        return textChangedEventFlowable(filter);
    }

    public List<MultihashChangedEventResponse> getMultihashChangedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(MULTIHASHCHANGED_EVENT, transactionReceipt);
        ArrayList<MultihashChangedEventResponse> responses = new ArrayList<MultihashChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MultihashChangedEventResponse typedResponse = new MultihashChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.hash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<MultihashChangedEventResponse> multihashChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, MultihashChangedEventResponse>() {
            @Override
            public MultihashChangedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(MULTIHASHCHANGED_EVENT, log);
                MultihashChangedEventResponse typedResponse = new MultihashChangedEventResponse();
                typedResponse.log = log;
                typedResponse.node = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.hash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<MultihashChangedEventResponse> multihashChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MULTIHASHCHANGED_EVENT));
        return multihashChangedEventFlowable(filter);
    }

    public static class AddrChangedEventResponse extends BaseEventResponse {
        public byte[] node;

        public String a;
    }

    public static class ContentChangedEventResponse extends BaseEventResponse {
        public byte[] node;

        public byte[] hash;
    }

    public static class NameChangedEventResponse extends BaseEventResponse {
        public byte[] node;

        public String name;
    }

    public static class ABIChangedEventResponse extends BaseEventResponse {
        public byte[] node;

        public BigInteger contentType;
    }

    public static class PubkeyChangedEventResponse extends BaseEventResponse {
        public byte[] node;

        public byte[] x;

        public byte[] y;
    }

    public static class TextChangedEventResponse extends BaseEventResponse {
        public byte[] node;

        public String indexedKey;

        public String key;
    }

    public static class MultihashChangedEventResponse extends BaseEventResponse {
        public byte[] node;

        public byte[] hash;
    }
}
