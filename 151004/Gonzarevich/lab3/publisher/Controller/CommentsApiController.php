<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpKernel\Attribute\MapRequestPayload;
use Symfony\Contracts\HttpClient\Exception\ClientExceptionInterface;
use Symfony\Contracts\HttpClient\HttpClientInterface;
use Symfony\Component\Routing\Annotation\Route;
use Doctrine\ORM\EntityManagerInterface;

use App\Trait\JsonEndpointTrait;
use App\Validation\Requirement as ParamRules;

#[Route('/comments', name: 'comments_', options: ['expose' => true])]
class CommentsApiController extends AbstractController
{
    use JsonEndpointTrait;

    function __construct(
        private HttpClientInterface $client
    ) {}

    #[Pure]
    private function failureResponse(int $statusCode = JsonResponse::HTTP_NOT_FOUND): JsonResponse {

        $response = $this->cje(
            status: false,
        );
        return new JsonResponse($response, $statusCode);
    }

    #[Route('', name: 'show_all', methods: 'GET')]
    public function show_all(): JsonResponse
    {

        $response = $this->client->request('GET', "http://dcMicroserviceServer/api/v1.0/comments");
        
        return new JsonResponse(json_decode($response->getContent()), $response->getStatusCode());
    }

    #[Route('/{id}', name: 'show', methods: 'GET', requirements: ['id' => ParamRules::DIGITS])]
    public function show(int $id): JsonResponse 
    {

        $response = $this->client->request('GET', "http://dcMicroserviceServer/api/v1.0/comments/{$id}");
   
        return new JsonResponse(json_decode($response->getContent()), $response->getStatusCode());
    }

    #[Route('', name: 'store', methods: 'POST')]
    public function store(Request $request): JsonResponse 
    {

        try {
;
            $response = $this->client->request('POST', 'http://dcMicroserviceServer/api/v1.0/comments', [
                'headers'   => $request->headers->all(),
                'body'      => $request->getContent(),
            ]);
            
            return new JsonResponse(json_decode($response->getContent()), $response->getStatusCode());

        } catch (ClientExceptionInterface $exception) {
           
            return $this->failureResponse($exception->getResponse()->getStatusCode());
        }  
    }

    #[Route('', name: 'update', methods: ['PUT', 'PATCH'])]
    public function update(Request $request): JsonResponse 
    {

        try {

            $response = $this->client->request('PUT', "http://dcMicroserviceServer/api/v1.0/comments", [
                'headers'   => $request->headers->all(),
                'body'      => $request->getContent(),
            ]);

            return new JsonResponse(json_decode($response->getContent()), $response->getStatusCode());

        } catch (ClientExceptionInterface $exception) {

            return $this->failureResponse($exception->getResponse()->getStatusCode());
        }

    }

    #[Route('/{id}', name: 'destroy', methods: 'DELETE', requirements: ['id' => ParamRules::DIGITS])]
    public function destroy(int $id, Request $request): JsonResponse
    {

        try {

            $response = $this->client->request('DELETE', "http://dcMicroserviceServer/api/v1.0/comments/{$id}", [
                'headers'   => $request->headers->all(),
                'body'      => $request->getContent(),
            ]);

            return new JsonResponse(json_decode($response->getContent()), $response->getStatusCode());

        } catch (ClientExceptionInterface $exception) {

            return $this->failureResponse($exception->getResponse()->getStatusCode());
        }

    }

}
