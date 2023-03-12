package com.example.pokemonqrcode;

public interface FireStoreResults {
    public void onResultGet(boolean result);
}
/*
        Context now = this.getApplicationContext();


        FireStoreAuthentication f = new FireStoreAuthentication();
        final boolean[] b = new boolean[1];
        f.validUsername("Admin", new FireStoreResults() {
            @Override
            public void onResultGet(boolean result) {
                 Log.d("working", "the result for the username is ===> " + String.valueOf(result));
                 b[0] = result;
                 boolean a = b[0];
                 Toast.makeText(now, String.valueOf(a), Toast.LENGTH_SHORT).show();
            }
        });
 */