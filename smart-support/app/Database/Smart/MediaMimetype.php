<?php
/**
 */
namespace App\Database\Smart;

/**
 */
class MediaMimetype extends AbstractModel
{
	/**
	 */
	protected $table = 'media_mimetype';
	
	/**
	 */
	protected $visible = [
		'id',
		'name'
	];
}