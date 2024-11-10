<?php
/**
 */
namespace App\Support;

use Illuminate\Support\Facades\Request;

/**
 */
abstract class Uri
{
	/**
	 */
	protected static $isSecure;
	protected static $isBasepath;
	protected static $domain;
	protected static $domainWww;
	protected static $basePath;
	protected static $path;

	/**
	 */
	public static function parseHostIncludeWww($host){
		if (strpos($host, 'www.') !== 0) {
			$host = 'www.' . $host;
		}
		return $host;
	}

	/**
	 */
	public static function parseHostExcludeWww($host){
		if (strpos($host, 'www.') === 0) {
			$host = substr($host, 4);
		}
		return $host;
	}

	/**
	 */
	public static function parseDomainIncludeWww($uri){
		$host = parse_url($uri, PHP_URL_HOST);
		if ($host && strpos($host, 'www.') !== 0) {
			$host = 'www.' . $host;
		}
		return $host;
	}

	/**
	 */
	public static function parseDomainExcludeWww($uri){
		$host = parse_url($uri, PHP_URL_HOST);
		if ($host && strpos($host, 'www.') === 0) {
			$host = substr($host, 4);
		}
		return $host;
	}

	/**
	 */
	public static function isSecure(){
		if (static::$isSecure === null) {
			static::$isSecure = Request::isSecure();
		}
		return static::$isSecure;
	}

	/**
	 */
	public static function isBasepath(){
		if (static::$isBasepath === null) {
			static::$isBasepath = strlen(static::getBasePath()) === 0;
		}
		return static::$isBasepath;
	}

	/**
	 */
	public static function getDomain(){
		if (static::$domain === null) {
			static::$domain = static::parseHostExcludeWww(Request::getHost());
		}
		return static::$domain;
	}

	/**
	 */
	public static function getDomainWww(){
		if (static::$domainWww === null) {
			static::$domainWww = static::parseHostIncludeWww(Request::getHost());
		}
		return static::$domainWww;
	}

	/**
	 */
	public static function getBasePath(){
		if (static::$basePath === null) {
			static::$basePath = Request::getBasePath();
		}
		return static::$basePath;
	}

	/**
	 */
	public static function getPath(){
		if (static::$path === null) {
			static::$path = Request::path();
		}
		return static::$path;
	}
	
	/**
	 */
	public static function isSameDomain($uri){
		if (!$domainY = static::parseDomainExcludeWww($uri)) return false;
		if (!$domainX = static::getDomain()) return false;
		return $domainX == $domainY;
	}
	
	/**
	 */
	public static function isSameDomainWww($uri){
		if (!$domainY = static::parseDomainIncludeWww($uri)) return false;
		if (!$domainX = static::getDomainWww()) return false;
		return $domainX == $domainY;
	}

	/**
	 */
	public static function isSameDomainOf($uriX, $uriY){
		if (!$domainY = static::parseDomainExcludeWww($uriY)) return false;
		if (!$domainX = static::parseDomainExcludeWww($uriX)) return false;
		return $domainX == $domainY;
	}

	/**
	 */
	public static function isSameDomainWwwOf($uriX, $uriY){
		if (!$domainY = static::parseDomainIncludeWww($uriY)) return false;
		if (!$domainX = static::parseDomainIncludeWww($uriX)) return false;
		return $domainX == $domainY;
	}
}