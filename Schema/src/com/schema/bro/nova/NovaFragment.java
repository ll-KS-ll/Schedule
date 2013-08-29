package com.schema.bro.nova;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.schema.bro.R;
import com.schema.bro.ks.TextLoaderAnimator;

public class NovaFragment extends Fragment {

	private View view;
	private Bitmap novaImage;
	private TextLoaderAnimator animator = null;
	private boolean ready = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.nova_fragment, container, false);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (animator == null) {
			final ImageView imageView = (ImageView) view
					.findViewById(R.id.novaFragmentImageViewLoading);
			animator = new TextLoaderAnimator(getActivity(), imageView,
					"Laddar", 32);
			ready = true;
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		ready = false;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		ready = false;
		if (novaImage != null)
			novaImage.recycle();
		novaImage = null;
	}

	public void notifyDownload() {
		if (ready) {
			animator.changeText("Laddar");
			animator.stop();

			final ImageView image = (ImageView) getView().findViewById(
					R.id.novaFragmentImageViewPicture);
			final ImageView imageView = (ImageView) getView().findViewById(
					R.id.novaFragmentImageViewLoading);

			imageView.animate().alpha(1).withLayer()
					.withEndAction(new Runnable() {
						public void run() {
							animator.changeText("Laddar");
						}
					});
			image.animate().alpha(0).withLayer();
		}
	}

	public void setImageView(Bitmap bitmap) {
		if (bitmap == null) {
			Log.e("NovaFragment", "Bitmap is null");
			animator.changeText("Kan inte ladda bild");
			animator.stop();

			if (getView() == null) {
				Log.e("NovaFragment", "View is null");
				return;
			}

			final ImageView image = (ImageView) getView().findViewById(
					R.id.novaFragmentImageViewPicture);
			final ImageView imageView = (ImageView) getView().findViewById(
					R.id.novaFragmentImageViewLoading);
			if (image != null) {
				imageView.animate().setDuration(500);
				image.animate().setDuration(500);

				imageView.animate().alpha(1).withLayer();
				image.animate().alpha(0).withLayer();
			}
			return;
		}

		novaImage = Bitmap.createBitmap(bitmap);

		if (getView() == null) {
			Log.e("NovaFragment", "View is null");
			return;
		}

		final ImageView image = (ImageView) getView().findViewById(R.id.novaFragmentImageViewPicture);
		final ImageView imageView = (ImageView) getView().findViewById(R.id.novaFragmentImageViewLoading);
		if (image != null) {
			animator.stop();

			imageView.animate().setDuration(500);
			image.animate().setDuration(500);

			imageView.setAlpha(1f);
			image.setAlpha(0f);

			imageView.setImageDrawable(animator.getImage());
			image.setImageDrawable(new BitmapDrawable(getResources(), novaImage));

			imageView.animate().alpha(0).withLayer();
			image.animate().alpha(1).withLayer();
		} else {
			Log.e("NovaFragment", "ImageView is null");
		}
		ready = true;
	}

}
