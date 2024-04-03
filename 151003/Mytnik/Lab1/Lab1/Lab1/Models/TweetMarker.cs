using Lab1.Models;
using System;
using System.Collections.Generic;

namespace Lab1;

public partial class TweetMarker
{
    public int Id { get; set; }

    public int? TweetId { get; set; }

    public int? MarkerId { get; set; }

    public virtual Marker? Marker { get; set; }

    public virtual Tweet? Tweet { get; set; }
}
