<?php
/**
 */
namespace App\Database\Smart;
use Rorecek\Ulid\HasUlid;

/**
 */
class Media extends AbstractModel
{
	use HasUlid;
	/**
	 */
	protected $table = 'media';

	/**
	 */
	protected $primaryKeys = [
		'id' => 'id',
		'user_id' => 'user_id'
	];

	/**
	 */
	protected $casts = [
		'id' => 'string',
		'sku' => 'string'
	];

	/**
	 */
	protected $mediaPath;
	protected $mediaStatus;
	protected $mediaMimetype;

	/**
	 */
	public function mediaPath(){
		return MediaPath::where([
			'media_path.id' => $this->media_path_id
		]);
	}

	/**
	 */
	public function getMediaPath(){
		if ($this->mediaPath == null) {
			$this->mediaPath = $this->mediaPath()->first();
		}
		return $this->mediaPath;
	}

	/**
	 */
	public function mediaStatus(){
		return MediaStatus::where([
			'media_status.id' => $this->media_status_id
		]);
	}

	/**
	 */
	public function getMediaStatus(){
		if ($this->mediaStatus == null) {
			$this->mediaStatus = $this->mediaStatus()->first();
		}
		return $this->mediaStatus;
	}

	/**
	 */
	public function mediaMimetype(){
		return MediaMimetype::where([
			'media_mimetype.id' => $this->media_mimetype_id
		]);
	}
	
	/**
	 */
	public function getMediaMimetype(){
		if ($this->mediaMimetype == null) {
			$this->mediaMimetype = $this->mediaMimetype()->first();
		}
		return $this->mediaMimetype;
	}
}