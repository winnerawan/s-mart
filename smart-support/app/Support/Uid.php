<?php
/**
 */
namespace App\Support;

/**
 */
abstract class Uid
{
	/**
	 */
	const FORMAT = 'YmdHis';
	const RANDOM_ALPHA = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
	const RANDOM_NUMBER = '01234567890123456789012345678901234567890123456789012345678901';
	
	/**
	 */
	public static function alpha($length = 14){
		return gmdate(static::FORMAT) . static::randomAlpha($length);
	}

	/**
	 */
	public static function number($length = 14){
		return gmdate(static::FORMAT) . static::randomNumber($length);
	}

	/**
	 */
	public static function randomAlpha($length){
		return substr(str_shuffle(static::RANDOM_ALPHA), 0, $length);
	}

	/**
	 */
	public static function randomNumber($length){
		return substr(str_shuffle(static::RANDOM_NUMBER), 0, $length);
	}
}