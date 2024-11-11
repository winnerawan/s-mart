<?php
/*-------------------------------------------------------------------------
 * /stock
 *-------------------------------------------------------------------------
 */
$router->get('/data', [
	'as' => '/data',
	'uses' => 'StockController@data'
]);

$router->post('/create', [
	'as' => '/create',
	'uses' => 'StockController@create'
]);

$router->post('/update', [
	'as' => '/update',
	'uses' => 'StockController@update'
]);

$router->post('/updatePosition', [
	'as' => '/updatePosition',
	'uses' => 'StockController@updatePosition'
]);

$router->post('/delete', [
	'as' => '/delete',
	'uses' => 'StockController@delete'
]);