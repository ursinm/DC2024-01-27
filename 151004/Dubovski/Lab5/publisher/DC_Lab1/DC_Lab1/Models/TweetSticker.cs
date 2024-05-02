using DC_Lab1.Models;
using System;
using System.Collections.Generic;

namespace DC_Lab1;

public partial class TweetSticker
{
    public int Id { get; set; }

    public int? tweetId { get; set; }

    public int? stickerId { get; set; }

    public virtual Sticker? Sticker { get; set; }

    public virtual Tweet? Tweet { get; set; }
}
