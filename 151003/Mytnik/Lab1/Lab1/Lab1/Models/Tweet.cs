using System;
using System.Collections.Generic;
using Lab1.Models;

namespace Lab1;

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

    public virtual ICollection<TweetMarker> TweetMarkers { get; set; } = new List<TweetMarker>();

}
