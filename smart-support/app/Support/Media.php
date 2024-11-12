<?php
/**
 */
namespace App\Support;

use App\Database\Smart;
use App\Service\Api\Config;

/**
 */
abstract class Media
{
	/**
	 */
	public static function buildFitUrl(Smart\Media $media, $width, $height, $position = 'c'){
		return vsprintf('%s%s',[
			Config::get('media.fit'), $media->file
		]);
	}
	
	/**
	 */
	public static function buildFitUrlFromUser(Smart\User $user, $width, $height, $position = 'c'){
		$userPhoto = $user->getUserPhoto();
		if (!!$userPhoto) {
			return static::buildFitUrlFromUserPhoto($userPhoto, $width, $height, $position);
		}
	}

	/**
	 */
	public static function buildFitUrlFromUserPhoto(Smart\UserPhoto $userPhoto, $width, $height, $position = 'c'){
		$media = $userPhoto->getMedia();
		if (!!$media) {
			return static::buildFitUrl($media, $width, $height, $position);
		}
	}

	/**
	 */
	public static function buildFileUrl(Smart\Media $media){
		return vsprintf('%s%s/%s',[
			Config::get('media.file'), $media->getMediaPath()->name, $media->file
		]);
	}

	/**
	 */
	public static function buildFileUrlFromUser(Smart\User $user){
		$userPhoto = $user->getUserPhoto();
		if (!!$userPhoto) {
			return static::buildFileUrlFromUserPhoto($userPhoto);
		}
	}

	/**
	 */
	public static function buildFileUrlFromUserPhoto(Smart\UserPhoto $userPhoto){
		$media = $userPhoto->getMedia();
		if (!!$media) {
			return static::buildFileUrl($media);
		}
	}
	
	/**
	 */
	public static function buildFileUrlFromItemMedia(Smart\ItemMedia $itemMedia){
		$media = $itemMedia->getMedia();
		if (!!$media) {
			return static::buildFileUrl($media);
		}
	}
}