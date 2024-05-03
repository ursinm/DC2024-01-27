<?php

namespace App\DTO;

use Symfony\Component\Validator\Constraints as Assert;
use App\Attribute\Request\EntityRelation;
use App\Entity\Issue;

class CommentRequestTo
{
    public function __construct(

        public readonly ?int $id,

        #[EntityRelation(Issue::class)]
        public readonly ?int $issueId,

        #[Assert\NotBlank]
        #[Assert\Length(min: 4, max: 2048)]
        public readonly string $content
    ) {
    }
}