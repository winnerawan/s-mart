<?php
/**
 */
namespace App\Support;

use Illuminate\Support\Facades;

/**
 */
abstract class Config
{
	/**
	 */
	const PRODUCTION = 'production';
	const DEVELOPMENT = 'development';

	/**
	 */
	protected static $locale;
	protected static $isDebug;
	protected static $isProduction;
	protected static $isDevelopment;
	
	/**
	 */
	public static function locale(){
		if (static::$locale === null) {
			static::$locale = Facades\Config::get('app.locale', '');
		}
		return static::$locale;
	}

	/**
	 */
	public static function isDebug(){
		if (static::$isDebug === null) {
			static::$isDebug = Facades\Config::get('app.debug', false);
		}
		return static::$isDebug;
	}

	/**
	 */
	public static function isProduction(){
		if (static::$isProduction === null) {
			static::$isProduction = Facades\Config::get('app.env') == static::PRODUCTION;
		}
		return static::$isProduction;
	}

	/**
	 */
	public static function isDevelopment(){
		if (static::$isDevelopment === null) {
			static::$isDevelopment = Facades\Config::get('app.env') == static::DEVELOPMENT;
		}
		return static::$isDevelopment;
	}
}