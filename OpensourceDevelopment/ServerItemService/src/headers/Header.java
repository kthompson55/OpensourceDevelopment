package headers;

public enum Header 
{
	DATE_ERROR
	{
		@Override
		public String getHeader() 
		{
			return "401";
		}
	},
	PRICE_ERROR
	{
		@Override
		public String getHeader() 
		{
			return "402";
		}
	},
	DATE_AND_PRICE_ERROR
	{
		@Override
		public String getHeader() 
		{
			return "403";
		}
	},
	ITEM_SERVICE_ERROR
	{
		@Override
		public String getHeader() 
		{
			return "404";
		}
	},
	OKAY
	{
		@Override
		public String getHeader() 
		{
			return "300";
		}
	};
	
	public abstract String getHeader();
	public String getNumElements(int numElements)
	{
		return numElements+"";
	}
}