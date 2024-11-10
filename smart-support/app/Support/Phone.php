<?php
/**
 */
namespace App\Support;

use libphonenumber\PhoneNumberUtil;
use libphonenumber\PhoneNumberFormat;

/**
 */
abstract class Phone
{
	/**
	 */
	protected static $utiler;
	protected static $region = 'ID';

	/**
	 */
	public static function setUtiler($utiler){
		static::$utiler = $utiler;
	}

	/**
	 */
	public static function getUtiler(){
		if (static::$utiler == null) {
			static::setUtiler(PhoneNumberUtil::getInstance());
		}
		return static::$utiler;
	}
	
	/**
	 */
	public static function setRegion($region){
		static::$region = $region;
	}

	/**
	 */
	public static function getRegion(){
		return static::$region;
	}

	/**
	 */
	public static function parse($value){
		return static::getUtiler()->parse($value, static::$region);
	}

	/**
	 */
	public static function format($value, $format = PhoneNumberFormat::E164){
		return static::getUtiler()->format(static::parse($value), $format);
	}
}