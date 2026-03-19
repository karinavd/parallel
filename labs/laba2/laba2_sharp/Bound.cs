namespace ThreadMinSharp
{
        class Bound
        {
            public Bound(long startIndex, long finishIndex)
            {
                StartIndex = startIndex;
                FinishIndex = finishIndex;
            }

            public long StartIndex { get; set; }
            public long FinishIndex { get; set; }
        }
}