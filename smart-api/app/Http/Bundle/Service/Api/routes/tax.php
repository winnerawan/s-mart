<?php
/*-------------------------------------------------------------------------
 * /tax
 *-------------------------------------------------------------------------
 */
$router->get('/data', [
	'as' => '/data',
	'uses' => 'TaxController@data'
]);

$router->post('/create', [
	'as' => '/create',
	'uses' => 'TaxController@create'
]);

$router->post('/update', [
	'as' => '/update',
	'uses' => 'TaxController@update'
]);

$router->post('/updatePosition', [
	'as' => '/updatePosition',
	'uses' => 'TaxController@updatePosition'
]);

$router->post('/delete', [
	'as' => '/delete',
	'uses' => 'TaxController@delete'
]);