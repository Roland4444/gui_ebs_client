package app.abstractions;

import Message.BKKCheck.ResponceMessage;

public  interface OnFailure{
    public void failed(ResponceMessage resp);
}
