using DC_Lab1.Models;
using System;
using System.Collections.Generic;

namespace DC_Lab1;

public partial class TweetMarker
{
    public int Id { get; set; }

    public int? tweetId { get; set; }

    public int? markerId { get; set; }

    public virtual Marker? Marker { get; set; }

    public virtual Tweet? Tweet { get; set; }
}
