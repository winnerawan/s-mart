<?php
/**
 */
namespace App\Support;

use Illuminate\Support\Facades\Request;

/**
 */
abstract class Curl
{
	/**
	 */
	public static function get($url, array $params = []){
		$ch = curl_init(); 
		curl_setopt($ch, CURLOPT_URL, sprintf('%s?%s', $url, http_build_query($params)));
		curl_setopt($ch, CURLOPT_HEADER, 0);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
		curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 10);
		$result = curl_exec($ch);
		curl_close($ch);
		return $result;
	}

	/**
	 */
	public static function post($url, array $params = []){
		$ch = curl_init(); 
		curl_setopt($ch, CURLOPT_URL, $url);
		curl_setopt($ch, CURLOPT_HEADER, 0);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
		curl_setopt($ch, CURLOPT_CONNECTTIMEOUT, 10);
		curl_setopt($ch, CURLOPT_POST, count($params));
		curl_setopt($ch, CURLOPT_POSTFIELDS, $params);
		$result = curl_exec($ch);
		curl_close($ch);
		return $result;
	}
}