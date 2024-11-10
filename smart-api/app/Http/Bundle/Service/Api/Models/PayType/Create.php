<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\PayType;

use Carbon\Carbon;
use Illuminate\Http\Request;
use App\Database\Smart;
use App\Service\Api;
use App\Support;

/**
 */
class Create extends Api\User\UserValidator
{
    /**
	 */
	protected $validatorRules = [
        'name' => 'required|string',
    ];

    protected $payType;

     /**
	 */
	public function validate(){
		if (!parent::validate()) {
			return false;
		}
        return true;
    }

    /**
     */
    public function create() {
        return Smart\User::transaction(function(){
            $now = \Carbon\Carbon::now();
            $this->payType = new Smart\PayType();
            $this->payType->name = $this->validatorData->get('name');
            // $this->payType->value = $this->validatorData->get('value');
            $this->payType->save();
		});
    }

    

}