<?php
/**
 */
namespace App\Database\Smart;

/**
 */
class MediaPath extends AbstractModel
{
	/**
	 */
	protected $table = 'media_path';
	
	/**
	 */
	protected $visible = [
		'id',
		'name'
	];

	/**
	 */
	protected $mediaPathStatus;

	/**
	 */
	public function mediaPathStatus(){
		return MediaPathStatus::where([
			'media_path_status.id' => $this->media_path_status_id
		]);
	}

	/**
	 */
	public function getMediaPathStatus(){
		if ($this->mediaPathStatus == null) {
			$this->mediaPathStatus = $this->mediaPathStatus()->first();
		}
		return $this->mediaPathStatus;
	}
}