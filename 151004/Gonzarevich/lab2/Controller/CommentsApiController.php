<?php

namespace App\Controller;

use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\HttpFoundation\JsonResponse as Response;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpKernel\Attribute\MapRequestPayload;
use App\Trait\JsonEndpointTrait;
use App\Validation\Requirement as ParamRules;

use App\Entity\{Comment, Issue};
use App\DTO\{CommentRequestTo, CommentResponseTo};
use App\Repository\CommentRepository;

#[Route('/comments', name: 'comments_', options: ['expose' => true])]
class CommentsApiController extends AbstractController
{
    use JsonEndpointTrait;

    function __construct(
        private CommentRepository $commentRepository
    ) {}

    #[Route('', name: 'show_all', methods: 'GET')]
    public function show_all(): Response
    {

        $comments = $this->commentRepository->findAll();

        $response = $this->constructArrayJsonEndpoint(
            entities: $comments,
        );

        return new Response($response, Response::HTTP_OK);
    }

    #[Route('/{id}', name: 'show', methods: 'GET', requirements: ['id' => ParamRules::DIGITS])]
    public function show(int $id): Response 
    {
        
        $comment = $this->commentRepository->find($id);

        $response = $this->constructJsonEndpoint(
            status: true,
            entity: $comment,
        );

        return new Response($response, Response::HTTP_OK);
       
    }

    #[Route('', name: 'store', methods: 'POST')]
    public function store(
        #[MapRequestPayload] CommentRequestTo $commentReview,
        EntityManagerInterface $entityManager
    ): Response
    {
        $comment = $this->MapRequestDTOToEntity($commentReview, $entityManager);
        
        $entityManager->persist($comment);
        $entityManager->flush();

        $response = $this->constructJsonEndpoint(
            status: true,
            entity: $comment,
        );
        
        return new Response($response, Response::HTTP_CREATED);
    }

    #[Route('', name: 'update', methods: ['PUT', 'PATCH'])]
    public function update(
        #[MapRequestPayload] CommentRequestTo $commentReview,
        EntityManagerInterface $entityManager
    ): Response 
    {

        $comment = $this->commentRepository->find($commentReview?->id);

        $response = null;
        if($comment == null) 
        {

            $response = $this->cje(
                status: false,
            );
            $response = new Response($response, Response::HTTP_NOT_FOUND);
        }
        else
        {

            $comment->setIssueId($entityManager->getRepository(Issue::class)->find($commentReview->issueId));
            $comment->setContent($commentReview->content);

            $entityManager->persist($comment);
            $entityManager->flush();

            $response = $this->constructJsonEndpoint(
                status: true,
                entity: $comment,
            );
            $response = new Response($response, Response::HTTP_OK);
        }
        return $response;
    }

    #[Route('/{id}', name: 'destroy', methods: 'DELETE', requirements: ['id' => ParamRules::DIGITS])]
    public function destroy(int $id, EntityManagerInterface $entityManager): Response
    {

        $comment = $this->commentRepository->find($id);
        $response = null;

        if($comment == null) 
        {
            $response = $this->cje(
                status: false,
            );
            $response = new Response($response, Response::HTTP_NOT_FOUND);
        }
        else
        {

            $entityManager->remove($comment);
            $entityManager->flush();

            $response = $this->cje(
                status: true,
            );
            $response = new Response($response, Response::HTTP_NO_CONTENT);
        }
        
        return $response;
    }

}
