package com.clonefish.cocktail.social;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObject;
import com.facebook.widget.FacebookDialog;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKRequest.VKRequestListener;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKWallPostResult;

public class ShareButton implements OnClickListener, OnMultiChoiceClickListener
{
	private SocialActivity mActivity;
	private String message;
	private String[] items = {"Facebook", "Вконтакте"};
	private boolean[] checkedItems = {false, false};
	
	public ShareButton(SocialActivity activity, String message)
	{
		this.mActivity = activity;
		this.message = message;
	}
	
	@Override
	public void onClick(View v) 
	{
		if(isFBLoggedIn() && VKSdk.isLoggedIn()) 
			showPickerDialog();
		else if(isFBLoggedIn())	
			postFB();
		else if(VKSdk.isLoggedIn())
			postVK(null, message);
	}
	
	private void showPickerDialog() 
	{
		AlertDialog.Builder pickerDialog = new AlertDialog.Builder(mActivity);
		pickerDialog.setTitle("Share cocktail to");
//		pickerDialog.setMessage("Chose social network to share for");
		pickerDialog.setMultiChoiceItems(items, checkedItems, this); 
		pickerDialog.setPositiveButton("Share", new DialogInterface.OnClickListener() 
		{
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(checkedItems[0]) postFB();
				if(checkedItems[1]) postVK(null, message);
			}
		});
		pickerDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		pickerDialog.create();
		pickerDialog.show();
	}

	@Override
	public void onClick(DialogInterface dialog, int which, boolean isChecked) 
	{
		checkedItems[which] = isChecked;
	}
	
	public boolean isFBLoggedIn() 
	{
	    Session session = Session.getActiveSession();
	    if (session != null && session.isOpened()) {
	        return true;
	    } else {
	        return false;
	    }
	}
	
	private void postFB() {
//            FacebookDialog shareDialog = createShareDialogBuilder().build();
//            mActivity.getFbHelper().trackPendingDialogCall(shareDialog.present());
            
            Request request = Request.newStatusUpdateRequest(Session.getActiveSession(), message, new Request.Callback() 
            	{
            		@Override
                    public void onCompleted(Response response) 
            		{
            			showPublishResult(message, response.getGraphObject(), response.getError());
                    }
                });
            request.executeAsync();
    }
	
	private void showPublishResult(String message, GraphObject result, FacebookRequestError error) {
		String title = null;
		String alertMessage = null;
		if (error == null) {
			title = "все получилось";
			alertMessage = "пост отправлен";
		} else {
			title = "все пропало!";
			alertMessage = error.getErrorMessage();
		}
		
		new AlertDialog.Builder(mActivity)
			.setTitle(title)	
			.setMessage(alertMessage)
			.setPositiveButton("Ok", null)
			.show();
	}
	
	private void postVK(String attachments, String message) {
        VKRequest post = VKApi.wall().post(VKParameters.from(VKApiConst.OWNER_ID, mActivity.getVKUserId(), VKApiConst.ATTACHMENTS, attachments, VKApiConst.MESSAGE, message));
        post.setModelClass(VKWallPostResult.class);
        post.executeWithListener(new VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) 
            {
                super.onComplete(response);
            }

            @Override
            public void onError(VKError error) {
                showError(error);
            }
        });
    }
	
	private void showError(VKError error) {
        new AlertDialog.Builder(mActivity)
                .setMessage(error.errorMessage)
                .setPositiveButton("OK", null)
                .show();

        if (error.httpError != null) {
            Log.w("Test", "Error in request or upload", error.httpError);
        }
    }
	
	
	private interface GraphObjectWithId extends GraphObject {
        String getId();
    }
	
	private FacebookDialog.ShareDialogBuilder createShareDialogBuilder() {
        return new FacebookDialog.ShareDialogBuilder(mActivity)
                .setName("Hello Facebook")
                .setDescription("The 'Hello Facebook' sample application showcases simple Facebook integration")
                .setLink("http://developers.facebook.com/android");
    }

}
