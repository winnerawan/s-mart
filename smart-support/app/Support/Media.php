<?php
/**
 */
namespace App\Support;

use App\Database\Smart;
use App\Web\Config;

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
	public static function buildHelpFitUrl(Smart\HelpAttachment $media, $width, $height, $position = 'c'){
		return vsprintf('%s%s',[
			Config::get('media.fit'), $media->file
		]);
	}

	/**
	 */
	public static function buildHelpFileUrl(Smart\HelpAttachment $helpAttachemnt, $width, $height, $position = 'c'){
		return vsprintf('%s%s',[
			Config::get('media.file'), $helpAttachemnt->getMedia()->file
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
	public static function buildFitUrlFromPublic(Smart\UserPublic $user, $width, $height, $position = 'c'){
		$userPhoto = $user->getPublicPhoto();
		if (!!$userPhoto) {
			return static::buildFitUrlFromPublicPhoto($userPhoto, $width, $height, $position);
		}
	}

	/**
	 */
	public static function buildFitUrlFromPublicPhoto(Smart\PublicPhoto $userPhoto, $width, $height, $position = 'c'){
		$media = $userPhoto->getMedia();
		if (!!$media) {
			return static::buildFitUrl($media, $width, $height, $position);
		}
	}

	/**
	 */
	public static function buildFitUrlFromHelp(Smart\Help $help, $width, $height, $position = 'c'){
		$helpAttachment = $help->getHelpAttachment();
		if (!!$helpAttachment) {
			return static::buildFitUrlFromHelpAttachment($helpAttachment, $width, $height, $position);
		}
	}

	/**
	 */
	public static function buildFitUrlFromHelpAttachment(Smart\HelpAttachment $helpAttachemnt, $width, $height, $position = 'c'){
		$media = $helpAttachemnt->getMedia();
		if (!!$media) {
			return static::buildHelpFitUrl($media, $width, $height, $position);
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
}