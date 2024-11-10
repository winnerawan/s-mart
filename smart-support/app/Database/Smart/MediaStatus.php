<?php
/**
 */
namespace App\Database\Smart;

/**
 */
class MediaStatus extends AbstractModel
{
	/**
	 */
	const STATUS_ACTIVE_ID = 1;
	const STATUS_REMOVED_ID = 2;
	const STATUS_TEMPORARY_ID = 3;

	/**
	 */
	protected $table = 'media_status';
	
	/**
	 */
	protected $visible = [
		'id',
		'name'
	];

	/**
	 */
	public function isStatusOf($id){
		return $this->id == $id;
	}

	/**
	 */
	public function isStatusActive(){
		return $this->isStatusOf(static::STATUS_ACTIVE_ID);
	}

	/**
	 */
	public function isStatusRemoved(){
		return $this->isStatusOf(static::STATUS_REMOVED_ID);
	}

	/**
	 */
	public function isStatusTemporary(){
		return $this->isStatusOf(static::STATUS_TEMPORARY_ID);
	}
}