// Copyright (C) 2014  Bonsai Software, Inc.
// 
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.

package com.bonsai.btcreceive;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.bitcoin.core.NetworkParameters;
import com.google.bitcoin.params.MainNetParams;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import eu.livotov.zxscan.ZXScanHelper;

public class ScanXPubActivity extends ActionBarActivity {

    private static Logger mLogger =
        LoggerFactory.getLogger(ScanXPubActivity.class);

    private Resources			mRes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        mRes = getApplicationContext().getResources();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan_xpub);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lobby_actions, menu);
		return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        Intent intent;
        switch (item.getItemId()) {
        case R.id.action_about:
            intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

	protected void onActivityResult(final int requestCode,
                                    final int resultCode,
                                    final Intent data)
    {
        if (resultCode != RESULT_OK || requestCode != 12347)
        {
            String msg = mRes.getString(R.string.scan_xpub_scanfail);
            mLogger.warn(msg);
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            return;
        }

        String scannedCode = ZXScanHelper.getScannedCode(data);

        JSONObject codeObj;
        try {
            codeObj = new JSONObject(scannedCode);
        } catch (JSONException ex) {
            String msg = "trouble deserializing pairing code: " + ex.toString();
            mLogger.error(msg);
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            return;
        }

        // Setup the wallet in a background task.
        new ScanXPubTask().execute(codeObj);
    }

    private class ScanXPubTask extends AsyncTask<JSONObject, Void, Void> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show
                (ScanXPubActivity.this, "",
                 mRes.getString(R.string.scan_xpub_wait_setup));
        }

		protected Void doInBackground(JSONObject... args)
        {
            final JSONObject codeObj = args[0];

            WalletApplication wallapp =
                (WalletApplication) getApplicationContext();
            NetworkParameters params = MainNetParams.get();
            String filePrefix = "btcreceive";

/*            // Setup a wallet with the restore seed.
            HDWallet hdwallet;
            try {
                hdwallet = new HDWallet(getApplicationContext(),
                                        params,
                                        getApplicationContext().getFilesDir(),
                                        filePrefix,
                                        wallapp.mKeyCrypter,
                                        wallapp.mAesKey,
                                        codeObj,
                                        true);
            } catch (JSONException ex) {
                String msg =
                    "trouble deserializing pairing obj: " + ex.toString();
                mLogger.error(msg);
                throw new RuntimeException(msg);
            }
            hdwallet.persist();
*/
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progressDialog.dismiss();


            // Spin up the WalletService.
            Intent svcintent = new Intent(ScanXPubActivity.this, WalletService.class);
            Bundle bundle = new Bundle();
            bundle.putString("SyncState", "RESTORE");
            svcintent.putExtras(bundle);
            startService(svcintent);

            Intent intent = new Intent(ScanXPubActivity.this, MainActivity.class);
            startActivity(intent);

            // Prevent the user from coming back here.
            finish();
        }
    }

    public void scanXPubCode(View view) {
        mLogger.info("scanning pairing code");

        // CaptureActivity
        ZXScanHelper.setCustomScanSound(R.raw.quiet_beep);
        ZXScanHelper.setCustomScanLayout(R.layout.scanner_layout);
        ZXScanHelper.scan(this, 12347);
    }
}
