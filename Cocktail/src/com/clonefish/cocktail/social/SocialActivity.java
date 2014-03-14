package com.clonefish.cocktail.social;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCaptchaDialog;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.VKSdkListener;
import com.vk.sdk.VKUIHelper;
import com.vk.sdk.api.VKError;

public class SocialActivity extends FragmentActivity {
	private UiLifecycleHelper fbUiHelper;
	private String VKUserId;

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {

		}
	};

	private final VKSdkListener sdkListener = new VKSdkListener() {
		@Override
		public void onCaptchaError(VKError captchaError) {
			new VKCaptchaDialog(captchaError).show();
		}

		@Override
		public void onTokenExpired(VKAccessToken expiredToken) {
			VKSdk.authorize(sMyScope);
		}

		@Override
		public void onAccessDenied(VKError authorizationError) {
			new AlertDialog.Builder(SocialActivity.this).setMessage(
					authorizationError.errorMessage).show();
		}

		@Override
		public void onReceiveNewToken(VKAccessToken newToken) 
		{
			VKUserId = newToken.userId;
		}

		@Override
		public void onAcceptUserToken(VKAccessToken token) 
		{
			VKUserId = token.userId;
		}
	};

	private static final String[] sMyScope = new String[] {
        VKScope.WALL,
        VKScope.NOHTTPS
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		fbUiHelper = new UiLifecycleHelper(this, callback);
		fbUiHelper.onCreate(savedInstanceState);

		VKSdk.initialize(sdkListener, "4228075");
		VKUIHelper.onCreate(this);

		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		fbUiHelper.onResume();
		VKUIHelper.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		fbUiHelper.onPause();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		fbUiHelper.onSaveInstanceState(outState);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		fbUiHelper.onDestroy();
		VKUIHelper.onDestroy(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		VKUIHelper.onActivityResult(requestCode, resultCode, data);
		fbUiHelper.onActivityResult(requestCode, resultCode, data,
				new FacebookDialog.Callback() {
					@Override
					public void onError(FacebookDialog.PendingCall pendingCall,
							Exception error, Bundle data) {
						Log.e("Activity",
								String.format("Error: %s", error.toString()));
					}

					@Override
					public void onComplete(
							FacebookDialog.PendingCall pendingCall, Bundle data) {
						Log.i("Activity", "Success!");
					}
				});
	}
	
	public UiLifecycleHelper getFbHelper()
	{
		return fbUiHelper;
	}
	
	public String getVKUserId()
	{
		return VKUserId;
	}
}
