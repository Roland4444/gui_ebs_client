package app.abstractions;

import java.io.IOException;

public
    interface OnSuccess{
        public void passed() throws IOException, InterruptedException;
    }

