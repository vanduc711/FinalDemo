package com.tutorial.finaldemo.config;

import com.tutorial.finaldemo.service.ContractService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.websocket.WebSocketService;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;
import java.net.ConnectException;

@Component
public class ContractConfig {

    public final BigInteger gasPrice = BigInteger.valueOf(200_000L);
    public final BigInteger gasLimit = BigInteger.valueOf(6_300_000);
    public final StaticGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);
    private Web3j web3j;
    private Credentials credentials;
    private WebSocketService webSocketService;
    private ContractService contractService;
    @Value("${rpcUrl}")
    private String rpcUrl;
    @Value("${privateKey}")
    private String privateKey;

    @Bean
    public void run() throws ConnectException {
        this.credentials = Credentials.create(privateKey);
        this.webSocketService = new WebSocketService(rpcUrl, false);
        webSocketService.connect();
        this.web3j = Web3j.build(webSocketService);
    }

    public Web3j web3j() {
        return this.web3j;
    }

    public Credentials credentials() {
        return this.credentials;
    }


//    public List<Type> decodeEventDataFromBlock(EthBlock.Block block, List<String> eventSignatures) throws Exception {
//        List<Type> allEventData = new ArrayList<>();
//
//        EthFilter ethFilter = new EthFilter(
//                DefaultBlockParameter.valueOf(block.getNumber()),
//                DefaultBlockParameter.valueOf(block.getNumber()),
//                contractService.getContractAddress());
//
//        for (String eventSignature : eventSignatures) {
//            ethFilter.addSingleTopic(eventSignature);
//        }
//
//        EthLog ethLog = web3j.ethGetLogs(ethFilter).send();
//        List<EthLog.LogResult> logs = ethLog.getLogs();
//
//        for (EthLog.LogResult logResult : logs) {
//            List<Type> eventData = decodeEventDataFromBlock((EthBlock.Block) logResult.get(), eventSignatures);
//            allEventData.addAll(eventData);
//        }
//
//        return allEventData;
//    }
//
//    public List<EthLog.LogResult> getAllBlocksForContract(String contractAddress) {
//        DefaultBlockParameter startBlock = DefaultBlockParameterName.EARLIEST;
//        DefaultBlockParameter endBlock = DefaultBlockParameterName.LATEST;
//
//        EthFilter ethFilter = new EthFilter(startBlock, endBlock, contractAddress);
//
//        try {
//            EthLog ethLog = web3j.ethGetLogs(ethFilter).send();
//            return ethLog.getLogs();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Bean
//    public Disposable subscribeToBlocks() {
//        return web3j.blockFlowable(true).subscribe(block -> {
//            EthBlock.Block ethBlock = block.getBlock();
//            System.out.println("New Block: " + ethBlock.getNumber());
//        });
//    }


}
