<?php
/**
 */
namespace App\Service\Api;

use Illuminate\Support\Facades;
use Illuminate\Config\Repository;

/**
 */
abstract class Config
{
	/**
	 */
	protected static $config;
	
	/**
	 */
	public static function all(){
		if (static::$config === null) {
			static::$config = new Repository(Facades\Config::get('service.api'));
		}
		return static::$config;
	}

	/**
	 */
	public static function set($key, $value){
		return  static::all()->set($key, $value);
	}
	
	/**
	 */
	public static function get($key, $default = null){
		return  static::all()->get($key, $default);
	}
	
	/**
	 */
	public static function getArray($key, array $default = null){
		return  (array)static::all()->get($key, $default);
	}

	/**
	 */
	public static function getRepository($key, array $default = null){
		return  new Repository(static::all()->get($key, $default));
	}
}