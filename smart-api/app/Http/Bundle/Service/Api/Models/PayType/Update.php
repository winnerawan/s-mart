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
class Update extends Api\User\UserValidator
{
    /**
	 */
	protected $validatorRules = [
        'payType' => 'required|numeric',
        'name' => 'nullable|string',
        'value' => 'nullable|numeric',
    ];

    protected $payType;

     /**
	 */
	public function validate(){
		if (!parent::validate()) {
			return false;
		}
        $this->payType = Smart\PayType::select()
            ->where('pay_type.id', $this->validatorData->get('payType'))
            ->first();
        if (!$this->payType) {
            return false;
        }
        return true;
    }

    /**
     */
    public function update() {
        return Smart\User::transaction(function(){
            $this->payType->name = $this->validatorData->get('name');
            // $this->payType->value = $this->validatorData->get('value');
            // $this->payType->updated_user_id = $this->getUser()->id;
            // $this->payType->is_updated = 1;
            $this->payType->save();
		});
    }
}