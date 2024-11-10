<?php
/**
 */
namespace App\Support\View\Minify;

/**
 */
abstract class Minify
{
	/**
	 */
	protected static $regExps = [
		/**
		 * CollapseWhitespace
		 */
		"/\n([\S])/" => '$1',
		"/\r/" => '',
		"/\n/" => '',
		"/\t/" => '',
		"/ +/" => ' ',
		"/> +</" => '><',
		/**
		 * RemoveComments
		 */
		'/<!--[^]><!\[](.*?)[^\]]-->/s' => ''
	];

	/**
	 */
	public static function minify($buffer){
		return preg_replace(array_keys(static::$regExps), array_values(static::$regExps), $buffer);
	}
}