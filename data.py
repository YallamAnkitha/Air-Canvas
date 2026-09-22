# loading the required libraries

import pandas as pd
import numpy as np

# create synthetic flood dataset
np.random.seed(42)
rows=1000

data={
   "Region":np.random.choice(["North","South","East","West"],rows),
   "Rainfall_mm":np.random.normal(250,80,rows).round(2),
   "River_levl_mm":np.random.normal(5,1.5,rows).round(2),
   "Soil_moistre_%":np.random.uniform(20,80,rows).round(2),
   "Temperature_c":np.random.uniform(30,5,rows).round(2),
   "Flood_occurred":np.random.choice([0,1],rows,p=[0.7,0.3]) # 0=no flood, 1=flood
}

df = pd.DataFrame(data)

#save as .csv file

df.to_csv("flood_dataset.csv",index=False)
print(" flood dataset csv file created successfully")