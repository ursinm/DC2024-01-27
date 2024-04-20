using System;
using System.Collections.Generic;
using Lab2.Models;

namespace Lab2;

public partial class Tweet
{
    public int Id { get; set; }

    public int? CreatorId { get; set; }

    public string? Title { get; set; }

    public string? Content { get; set; }

    public string? Created { get; set; }

    public string? Modified { get; set; }

    public virtual Creator? Creator { get; set; }

    public virtual ICollection<Note> Notes { get; set; } = new List<Note>();
}
