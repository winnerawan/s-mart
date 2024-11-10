<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Models\Tax;

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
        'value' => 'nullable|numeric'
    ];

    protected $tax;

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
            $this->tax = new Smart\Tax();
            $this->tax->name = $this->validatorData->get('name');
            $this->tax->value = $this->validatorData->get('value');
            $this->tax->save();
		});
    }

    

}