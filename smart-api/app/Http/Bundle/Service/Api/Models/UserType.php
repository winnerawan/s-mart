<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models;

use Carbon\Carbon;
use Illuminate\Http\Request;
use Wayang\Stdlib\Nik\Nik;
use App\Database\Smart;
use App\Service\Api;
use App\Support;
use App\Service;
use Exception;

/**
 */
class UserType extends Support\Validator\AbstractValidator
{

    protected $userTypes;

	/**
	 */
	public function validate(){
		if (!parent::validate()) {
			return false;
		}
        $this->userTypes = Smart\UserType::select()
            ->orderBy('user_type.name', 'DESC')
            ->whereIn('id', [Smart\UserType::TYPE_MEMBER_ID, Smart\UserType::TYPE_MEMBER_NON_PERMANENT_ID, Smart\UserType::TYPE_SECURITY_ID])
            ->get();
        return true;
    }

    /**
     */
    protected function wrapResponse($type) {
        $response = $type;
        // $response['image'] = $block->getBlockMedia() != null ? sprintf('https:%s%s', Api\Config::get('media.file'), $block->getBlockMedia()->getMedia()->file) : null;
        return $response;
    }
    /**
	 */
	protected function getData() {
		$response = [];
		foreach($this->userTypes as $type) {
			$response[] = $this->wrapResponse($type);
		}
		return $response;
	}
    /**
	 */
	public function response(){
        return $this->userTypes;
    }
}