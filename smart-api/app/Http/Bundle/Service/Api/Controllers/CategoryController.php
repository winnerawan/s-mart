<?php
/**
 */
namespace App\Http\Bundle\Service\Api\Controllers;

use Illuminate\Http\Request;
use App\Http\Bundle\Service\Api\Models;

/**
 */
class CategoryController extends AbstractController
{
    /*-------------------------------------------------------------------------
     * /category/data
     *-------------------------------------------------------------------------
     */
    public function data(Request $request)
    {
        $data = new Models\Category\Data($request);
        if (!$data->validate()) {
            return $this->jsonError()
                ->params($data->getValidator()->errors())
                ->build();
        }
        return $this->jsonData($data->response())
            ->build();
    }

    /*-------------------------------------------------------------------------
     * /category/create
     *-------------------------------------------------------------------------
     */
    public function create(Request $request)
    {
        $create = new Models\Category\Create($request);
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
     * /category/update
     *-------------------------------------------------------------------------
     */
    public function update(Request $request)
    {
        $update = new Models\Category\Update($request);
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
     * /category/updatePosition
     *-------------------------------------------------------------------------
     */
    public function updatePosition(Request $request)
    {
        $updatePosition = new Models\Category\Update($request);
        if (!$updatePosition->validate()) {
            return $this->jsonError()
                ->params($updatePosition->getValidator()->errors())
                ->build();
        }
        $updatePosition->updatePosition();
        return $this->jsonSuccess()
            ->build();
    }

    /*-------------------------------------------------------------------------
     * /category/delete
     *-------------------------------------------------------------------------
     */
    public function delete(Request $request)
    {
        $delete = new Models\Category\Delete($request);
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