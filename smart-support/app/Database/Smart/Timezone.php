<?php
/**
 */
namespace App\Database\Smart;

/**
 */
class Timezone extends AbstractModel
{	
	/**
	 */
	protected $table = 'timezone';
	
	/**
	 */
	protected $visible = [
		'id',
		'name',
		'value'
	];
}