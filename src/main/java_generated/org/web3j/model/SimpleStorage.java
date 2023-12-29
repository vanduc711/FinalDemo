package org.web3j.model;

import io.reactivex.Flowable;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
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

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.10.3.
 */
@SuppressWarnings("rawtypes")
public class SimpleStorage extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506104b4806100206000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c80633707cfc1146100515780634cc82215146100855780639507d39a146100a2578063f01fe692146100e0575b600080fd5b6100836004803603606081101561006757600080fd5b50803590602081013590604001356001600160a01b031661010c565b005b6100836004803603602081101561009b57600080fd5b5035610221565b6100bf600480360360208110156100b857600080fd5b5035610325565b604080519283526001600160a01b0390911660208301528051918290030190f35b610083600480360360408110156100f657600080fd5b50803590602001356001600160a01b031661039f565b600154831061015a576040805162461bcd60e51b8152602060048201526015602482015274149958dbdc9908191bd95cc81b9bdd08195e1a5cdd605a1b604482015290519081900360640190fd5b6000838152602081905260409020600201546001600160a01b031633146101b25760405162461bcd60e51b81526004018080602001828103825260268152602001806104336026913960400191505060405180910390fd5b6000838152602081815260409182902080546001600160a01b0319166001600160a01b03851690811782556001909101859055825185815292519092339287927fbabbf5eac333135a5c47470a2ad0fa75a65c56e75b0b48142de8b47d74a72a019281900390910190a4505050565b600154811061026f576040805162461bcd60e51b8152602060048201526015602482015274149958dbdc9908191bd95cc81b9bdd08195e1a5cdd605a1b604482015290519081900360640190fd5b6000818152602081905260409020600201546001600160a01b031633146102c75760405162461bcd60e51b81526004018080602001828103825260268152602001806104596026913960400191505060405180910390fd5b60008181526020819052604080822080546001600160a01b031990811682556001820184905560029091018054909116905551339183917f1b076ebf5cfaf50d2777dba584f7765da00499c8f95e3daea5f9bc1b3184b62d9190a350565b6000806001548310610376576040805162461bcd60e51b8152602060048201526015602482015274149958dbdc9908191bd95cc81b9bdd08195e1a5cdd605a1b604482015290519081900360640190fd5b5050600081815260208190526040902060018101546002909101546001600160a01b0316915091565b6001805460009081526020818152604080832084018690558354835280832060020180546001600160a01b031990811633908117909255855485529382902080546001600160a01b03881695168517905593548151878152915193949390927fbabbf5eac333135a5c47470a2ad0fa75a65c56e75b0b48142de8b47d74a72a01928290030190a45050600180548101905556fe4f6e6c79207468652063726561746f722063616e2075706461746520746865207265636f72644f6e6c79207468652063726561746f722063616e2064656c65746520746865207265636f7264a2646970667358221220a678d9d35098f1a2ec760a34fa1aab4d88f78266acf3cb62798dded03793658d64736f6c634300060c0033";

    public static final String FUNC_CREATE = "create";

    public static final String FUNC_GET = "get";

    public static final String FUNC_REMOVE = "remove";

    public static final String FUNC_UPDATE = "update";

    public static final Event VALUEDELETED_EVENT = new Event("ValueDeleted",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {
            }, new TypeReference<Address>(true) {
            }));
    ;

    public static final Event VALUEUPDATED_EVENT = new Event("ValueUpdated",
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {
            }, new TypeReference<Uint256>() {
            }, new TypeReference<Address>(true) {
            }, new TypeReference<Address>(true) {
            }));
    ;

    @Deprecated
    protected SimpleStorage(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SimpleStorage(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected SimpleStorage(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected SimpleStorage(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<ValueDeletedEventResponse> getValueDeletedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(VALUEDELETED_EVENT, transactionReceipt);
        ArrayList<ValueDeletedEventResponse> responses = new ArrayList<ValueDeletedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ValueDeletedEventResponse typedResponse = new ValueDeletedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.deleter = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ValueDeletedEventResponse getValueDeletedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(VALUEDELETED_EVENT, log);
        ValueDeletedEventResponse typedResponse = new ValueDeletedEventResponse();
        typedResponse.log = log;
        typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.deleter = (String) eventValues.getIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public static List<ValueUpdatedEventResponse> getValueUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(VALUEUPDATED_EVENT, transactionReceipt);
        ArrayList<ValueUpdatedEventResponse> responses = new ArrayList<ValueUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ValueUpdatedEventResponse typedResponse = new ValueUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.creator = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.contractAddress = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.newValue = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static ValueUpdatedEventResponse getValueUpdatedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(VALUEUPDATED_EVENT, log);
        ValueUpdatedEventResponse typedResponse = new ValueUpdatedEventResponse();
        typedResponse.log = log;
        typedResponse.id = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.creator = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.contractAddress = (String) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.newValue = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    @Deprecated
    public static SimpleStorage load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SimpleStorage(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static SimpleStorage load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SimpleStorage(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static SimpleStorage load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new SimpleStorage(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static SimpleStorage load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new SimpleStorage(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<SimpleStorage> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SimpleStorage.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SimpleStorage> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SimpleStorage.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<SimpleStorage> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(SimpleStorage.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<SimpleStorage> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SimpleStorage.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public Flowable<ValueDeletedEventResponse> valueDeletedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getValueDeletedEventFromLog(log));
    }

    public Flowable<ValueDeletedEventResponse> valueDeletedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VALUEDELETED_EVENT));
        return valueDeletedEventFlowable(filter);
    }

    public Flowable<ValueUpdatedEventResponse> valueUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getValueUpdatedEventFromLog(log));
    }

    public Flowable<ValueUpdatedEventResponse> valueUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VALUEUPDATED_EVENT));
        return valueUpdatedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> create(BigInteger newData, String contractAddress) {
        final Function function = new Function(
                FUNC_CREATE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(newData),
                        new org.web3j.abi.datatypes.Address(160, contractAddress)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple2<BigInteger, String>> get(BigInteger id) {
        final Function function = new Function(FUNC_GET,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(id)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {
                }, new TypeReference<Address>() {
                }));
        return new RemoteFunctionCall<Tuple2<BigInteger, String>>(function,
                new Callable<Tuple2<BigInteger, String>>() {
                    @Override
                    public Tuple2<BigInteger, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<BigInteger, String>(
                                (BigInteger) results.get(0).getValue(),
                                (String) results.get(1).getValue());
                    }
                });
    }

    public RemoteFunctionCall<TransactionReceipt> remove(BigInteger id) {
        final Function function = new Function(
                FUNC_REMOVE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(id)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> update(BigInteger id, BigInteger newData, String contractAddress) {
        final Function function = new Function(
                FUNC_UPDATE,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(id),
                        new org.web3j.abi.datatypes.generated.Uint256(newData),
                        new org.web3j.abi.datatypes.Address(160, contractAddress)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static class ValueDeletedEventResponse extends BaseEventResponse {
        public BigInteger id;

        public String deleter;
    }

    public static class ValueUpdatedEventResponse extends BaseEventResponse {
        public BigInteger id;

        public String creator;

        public String contractAddress;

        public BigInteger newValue;
    }
}
