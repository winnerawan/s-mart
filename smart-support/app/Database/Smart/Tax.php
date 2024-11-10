<?php
/**
 */
namespace App\Database\Smart;

/**
 */
class Tax extends AbstractModel
{
	/**
	 */
	protected $table = 'tax';
	
	/**
	 */
	protected $visible = [
		'id',
		'name',
		'value'
	];


}