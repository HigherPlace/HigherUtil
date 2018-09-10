package com.bryan.mvp.support.lce.impl.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.View;

import com.bryan.mvp.R;


@SuppressLint("NewApi")
public class AnimatorUtils {
	private volatile static AnimatorUtils lceAnimator;

	private AnimatorUtils() {
	}

	public static AnimatorUtils getInstance() {
		if (lceAnimator == null) {
			synchronized (AnimatorUtils.class) {
				if (lceAnimator == null) {
					lceAnimator = new AnimatorUtils();
				}
			}
		}
		return lceAnimator;
	}

	public void showLoading(View loadingView, View contentView, View errorView) {
		contentView.setVisibility(View.GONE);
		errorView.setVisibility(View.GONE);
		loadingView.setVisibility(View.VISIBLE);
	}

	public void showErrorView(@NonNull final View loadingView,
			@NonNull final View contentView, final View errorView) {

		contentView.setVisibility(View.GONE);

		final Resources resources = loadingView.getResources();
		// Not visible yet, so animate the view in
		AnimatorSet set = new AnimatorSet();
		ObjectAnimator in = ObjectAnimator.ofFloat(errorView, "alpha", 1f);
		ObjectAnimator loadingOut = ObjectAnimator.ofFloat(loadingView,
				"alpha", 0f);

		set.playTogether(in, loadingOut);
		set.setDuration(resources
				.getInteger(R.integer.lce_error_view_show_animation_time));

		set.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationStart(Animator animation) {
				super.onAnimationStart(animation);
				errorView.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				loadingView.setVisibility(View.GONE);
				loadingView.setAlpha(1f); // For future showLoading calls
			}
		});

		set.start();
	}

	public void showContent(final View loadingView, final View contentView,
			@NonNull final View errorView) {

		if (contentView.getVisibility() == View.VISIBLE) {
			// No Changing needed, because contentView is already visible
			errorView.setVisibility(View.GONE);
			loadingView.setVisibility(View.GONE);
		} else {

			errorView.setVisibility(View.GONE);

			final Resources resources = loadingView.getResources();
			final int translateInPixels = resources
					.getDimensionPixelSize(R.dimen.lce_content_view_animation_translate_y);
			// Not visible yet, so animate the view in
			AnimatorSet set = new AnimatorSet();
			ObjectAnimator contentFadeIn = ObjectAnimator.ofFloat(contentView,
					"alpha", 0f, 1f);
			ObjectAnimator contentTranslateIn = ObjectAnimator.ofFloat(
					contentView, "translationY", translateInPixels, 0);

			ObjectAnimator loadingFadeOut = ObjectAnimator.ofFloat(loadingView,
					"alpha", 1f, 0f);
			ObjectAnimator loadingTranslateOut = ObjectAnimator.ofFloat(
					loadingView, "translationY", 0, -translateInPixels);

			set.playTogether(contentFadeIn, contentTranslateIn, loadingFadeOut,
					loadingTranslateOut);
			set.setDuration(resources
					.getInteger(R.integer.lce_content_view_show_animation_time));

			set.addListener(new AnimatorListenerAdapter() {

				@Override
				public void onAnimationStart(Animator animation) {
					contentView.setTranslationY(0);
					loadingView.setTranslationY(0);
					contentView.setVisibility(View.VISIBLE);
				}

				@Override
				public void onAnimationEnd(Animator animation) {
					loadingView.setVisibility(View.GONE);
					loadingView.setAlpha(1f); // For future showLoading calls
					contentView.setTranslationY(0);
					loadingView.setTranslationY(0);
				}
			});

			set.start();
		}
	}
}
