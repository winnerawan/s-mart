<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Controllers;

use Illuminate\Http\Request;
use App\Http\Bundle\Service\Api\Models;

/**
 */
class PayTypeController extends AbstractController
{
    /*-------------------------------------------------------------------------
     * /paytype/data
     *-------------------------------------------------------------------------
     */
    public function data(Request $request)
    {
        $data = new Models\PayType\Data($request);
        if (!$data->validate()) {
            return $this->jsonError()
                ->params($data->getValidator()->errors())
                ->build();
        }
        return $this->jsonData($data->response())
            ->build();
    }

    /*-------------------------------------------------------------------------
     * /paytype/create
     *-------------------------------------------------------------------------
     */
    public function create(Request $request)
    {
        $create = new Models\PayType\Create($request);
        if (!$create->validate()) {
            return $this->jsonError()
                ->params($create->getValidator()->errors())
                ->build();
        }
        $create->create();
        return $this->jsonSuccess()
            ->build();
    }

    /*-------------------------------------------------------------------------
     * /paytype/update
     *-------------------------------------------------------------------------
     */
    public function update(Request $request)
    {
        $update = new Models\PayType\Update($request);
        if (!$update->validate()) {
            return $this->jsonError()
                ->params($update->getValidator()->errors())
                ->build();
        }
        $update->update();
        return $this->jsonSuccess()
            ->build();
    }


    /*-------------------------------------------------------------------------
     * /paytype/delete
     *-------------------------------------------------------------------------
     */
    public function delete(Request $request)
    {
        $delete = new Models\PayType\Delete($request);
        if (!$delete->validate()) {
            return $this->jsonError()
                ->params($delete->getValidator()->errors())
                ->build();
        }
        $delete->delete();
        return $this->jsonSuccess()
            ->build();
    }
}