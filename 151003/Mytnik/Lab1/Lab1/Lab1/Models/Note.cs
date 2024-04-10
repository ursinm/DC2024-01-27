using System;
using System.Collections.Generic;

namespace Lab1.Models;

public partial class Note
{
    public int Id { get; set; }

    public int? TweetId { get; set; }

    public string? Content { get; set; }

    public virtual Tweet? Tweet { get; set; }
}
