package app.Essens;

import Message.BKKCheck.ResponceMessage;
import Message.abstractions.BinaryMessage;
import Table.TablesEBSCheck;
import app.abstractions.OnFailure;
import app.abstractions.OnSuccess;
import app.abstractions.interop;
import app.utils.Cypher;
import impl.JAktor;

import javax.swing.*;
import java.io.IOException;
import java.util.Map;

public class AppAktor extends JAktor {
    public interop checkedViaForm;
    public OnSuccess on_success;
    public OnFailure on_failure;
    public JButton save;
    public TablesEBSCheck tebs = new TablesEBSCheck();
    private Cypher cypher;
    private Map<String, Integer> tableRequest;

    public void setTableReqs(Map<String, Integer> tableRequest){
        this.tableRequest=tableRequest;
    }
    public void setCypher(Cypher cypher){
        this.cypher=cypher;
    }

    @Override
    public int send(byte[] message, String address) throws IOException {
        return this.client.send(this.cypher.encrypt(message), address);
    }

    @Override
    public void receive(byte[] message_) throws IOException {
        System.out.println("Received!!!! via console");
        byte[] message =  cypher.decrypt(message_);
        ResponceMessage resp = (ResponceMessage) BinaryMessage.restored(message);
        System.out.println("\n\n\nRECEIVED");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (tableRequest.get(resp.ID)!=null){
            tableRequest.remove(resp.ID);
            tableRequest.put(resp.ID, resp.checkResult);
            if ((resp.checkResult==0))
                on_success.passed();
            else
                on_failure.failed(resp.checkResult);


        }
    }
}