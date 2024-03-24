using System;
using System.Collections.Generic;

namespace Lab1.Models;

public partial class Marker
{
    public int Id { get; set; }

    public string? Name { get; set; }

    public virtual ICollection<TweetMarker> TweetMarkers { get; set; } = new List<TweetMarker>();

}
