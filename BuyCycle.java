package BoringGame;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import java.util.concurrent.TimeUnit;

public class BuyCycle 
{
    protected Fields[][] fieldMatrix;
    protected ResourceMenu resourceMenu;
    protected BuyMenu buyMenu; 
    protected FamilyMenu familyMenu;
    protected RenderWindow window;
    protected Game game;
    protected boolean upgrade1Bought = false, upgrade2Bought = false, upgrade3Bought = false, upgrade4Bought = false;
    protected boolean carrotUnlocked = false, hempUnlocked = false, cauliflowerUnlocked = false;
    protected int hempIncrementVal = 13, chilliIncrementVal = 2, cauliflowerIncrementVal = 53, carrotIncrementVal = 5;
    protected int hempCost = 5, chilliCost = 1, cauliflowerCost = 13, carrotCost = 2;

    //Time increasing values for upgrades (lower the number the less time per cycle which leads to fast growth)
    private double upgrade2 = 0.75;
    private double upgrade3 = 0.65;

    public BuyCycle(Fields[][] array, ResourceMenu resMenu, BuyMenu buyMenuInput, FamilyMenu familyMenuInput, RenderWindow windowInput, Game gameInput) 
    {
        fieldMatrix = array;
        resourceMenu = resMenu;
        familyMenu = familyMenuInput;
        buyMenu = buyMenuInput;
        window = windowInput;
        game = gameInput;
    }

    /**
     * Chooses what vegetable to buy.
     * @param window the current game window for prespective
     * @return the array index of the vegetables
     */
    public int buyVeg(float mouseX, float mouseY)
    {
        int returnValue = -1;
        int decrementValue = 0;
        
        if(buyMenu.menuOpen == true)
        {
            if(mouseX >= buyMenu.vegIcons[0].getPosition().x && mouseX <= buyMenu.vegIcons[0].getPosition().x + buyMenu.vegIcons[0].getSize().x && mouseY >= buyMenu.vegIcons[0].getPosition().y && mouseY <= buyMenu.vegIcons[0].getPosition().y + buyMenu.vegIcons[0].getSize().y)
            {
                returnValue = 0;
                decrementValue = chilliCost;
            }
            else if(mouseX >= buyMenu.vegIcons[1].getPosition().x && mouseX <= buyMenu.vegIcons[1].getPosition().x + buyMenu.vegIcons[1].getSize().x && mouseY >= buyMenu.vegIcons[1].getPosition().y && mouseY <= buyMenu.vegIcons[1].getPosition().y + buyMenu.vegIcons[1].getSize().y)
            {
                if(resourceMenu.carrotLocked == true)
                {
                    returnValue = 1;
                    decrementValue = 5;
                }
                else
                {
                    returnValue = 1;
                    decrementValue = carrotCost;
                }
            }
            else if(mouseX >= buyMenu.vegIcons[2].getPosition().x && mouseX <= buyMenu.vegIcons[2].getPosition().x + buyMenu.vegIcons[2].getSize().x && mouseY >= buyMenu.vegIcons[2].getPosition().y && mouseY <= buyMenu.vegIcons[2].getPosition().y + buyMenu.vegIcons[2].getSize().y)
            {
                if(resourceMenu.carrotLocked == false && resourceMenu.hempLocked == true)
                {
                    returnValue = 2;
                    decrementValue = 10;
                }
                else if(resourceMenu.carrotLocked == false && resourceMenu.hempLocked == false)
                {
                    returnValue = 2;
                    decrementValue = hempCost;                    
                }
            }
            else if(mouseX >= buyMenu.vegIcons[3].getPosition().x && mouseX <= buyMenu.vegIcons[3].getPosition().x + buyMenu.vegIcons[3].getSize().x && mouseY >= buyMenu.vegIcons[3].getPosition().y && mouseY <= buyMenu.vegIcons[3].getPosition().y + buyMenu.vegIcons[3].getSize().y)
            {
                if(resourceMenu.carrotLocked == false && resourceMenu.hempLocked == false && resourceMenu.cauliflowerLocked == true)
                {
                    returnValue = 3;
                    decrementValue = 20;
                }
                else if(resourceMenu.carrotLocked == false && resourceMenu.hempLocked == false && resourceMenu.cauliflowerLocked == false)
                {
                    returnValue = 3;
                    decrementValue = cauliflowerCost;                
                }
            }
            
            System.out.println(decrementValue);
            if(returnValue != -1 && buyMenu.menuOpen == true && decrementValue != 0)
            {
                if(resourceMenu.getIndexVal(4) >= decrementValue)
                {
                    if(returnValue == 1)
                    {
                        resourceMenu.carrotLocked = false;   
                    }
                    else if(returnValue == 2)
                    {
                        resourceMenu.hempLocked = false;                        
                    }
                    else if(returnValue == 3)
                    {
                        resourceMenu.cauliflowerLocked = false;
                    }
                    
                    this.unlock(returnValue);                     
                    resourceMenu.increment(returnValue);
                    resourceMenu.decrement(4, decrementValue);
                    SoundEffect.PURCHASEITEM.play();
                    decrementValue = 0;
                }
            }
        }

        return returnValue;
    }

    /**
     * Chooses what upgrade to buy.
     * @param window the current game window for prespective
     * @return the array index of the upgrade
     */
    public int buyUpgrade(float mouseX, float mouseY)
    {
        int returnValue = -1;
        int decrementValue = 0;

        if(buyMenu.menuOpen == true)
        {
            if(upgrade1Bought == false && mouseX >= buyMenu.upgradeIcons[0].getPosition().x && mouseX <= buyMenu.upgradeIcons[0].getPosition().x + buyMenu.upgradeIcons[0].getSize().x && mouseY >= buyMenu.upgradeIcons[0].getPosition().y && mouseY <= buyMenu.upgradeIcons[0].getPosition().y + buyMenu.upgradeIcons[0].getSize().y)
            {
                //Upgrade no.1, makes plants one coin more profitable
                returnValue = 0;
                decrementValue = 5;
                
                if(resourceMenu.getIndexVal(4) >= decrementValue)
                {
                    System.out.println("test1");
                    upgrade1Bought = true;

                    carrotIncrementVal++;
                    chilliIncrementVal++;
                    hempIncrementVal++;
                    cauliflowerIncrementVal++;
                }

            }
            else if(upgrade1Bought == true && upgrade2Bought == false && carrotUnlocked == true && mouseX >= buyMenu.upgradeIcons[1].getPosition().x && mouseX <= buyMenu.upgradeIcons[1].getPosition().x + buyMenu.upgradeIcons[1].getSize().x && mouseY >= buyMenu.upgradeIcons[1].getPosition().y && mouseY <= buyMenu.upgradeIcons[1].getPosition().y + buyMenu.upgradeIcons[1].getSize().y)
            {
                
                //Upgrade no 2, decreases the growth time of each plant making growing these more efficient
                returnValue = 1;
                decrementValue = 10;
                
                if(resourceMenu.getIndexVal(4) >= decrementValue)
                {
                    System.out.println("test2");
                    upgrade2Bought = true;

                    for(int i = 0; i < 5; i++)
                    {
                        for(int z = 0; z < 5; z++)
                        {
                            for(int j = 0; j < fieldMatrix[i][z].clockArr.length; j++)
                            {
                                int temp = fieldMatrix[i][z].clockArr[j].time.getInitialDelay();
                                fieldMatrix[i][z].clockArr[j].time.setDelay((int)(temp * upgrade2));
                            }
                        }
                    }
                }


            }
            else if(upgrade1Bought == true && upgrade2Bought == true && upgrade3Bought == false && hempUnlocked == true && carrotUnlocked == true && mouseX >= buyMenu.upgradeIcons[2].getPosition().x && mouseX <= buyMenu.upgradeIcons[2].getPosition().x + buyMenu.upgradeIcons[2].getSize().x && mouseY >= buyMenu.upgradeIcons[2].getPosition().y && mouseY <= buyMenu.upgradeIcons[2].getPosition().y + buyMenu.upgradeIcons[2].getSize().y)
            {
                
                //Upgrade no 3, increses the profit and decreases the growth time, making the plants more profitable and growing said plants more time efficient
                returnValue = 2;
                decrementValue = 15;
                
                if(resourceMenu.getIndexVal(4) >= decrementValue)
                {
                    System.out.println("test3");
                    upgrade3Bought = true;

                    for(int i = 0; i < 5; i++)
                    {
                        for(int z = 0; z < 5; z++)
                        {
                            for(int j = 0; j < fieldMatrix[i][z].clockArr.length; j++)
                            {
                                int temp = fieldMatrix[i][z].clockArr[j].time.getInitialDelay();
                                fieldMatrix[i][z].clockArr[j].time.setDelay((int)(temp * upgrade3));
                            }
                        }
                    }
    
                    carrotIncrementVal += 2;
                    chilliIncrementVal += 2;
                    hempIncrementVal += 2;
                    cauliflowerIncrementVal += 2;
                }

            }
            else if(upgrade1Bought == true && upgrade2Bought == true && upgrade3Bought == true && hempUnlocked == true && carrotUnlocked == true && cauliflowerUnlocked == true && mouseX >= buyMenu.upgradeIcons[3].getPosition().x && mouseX <= buyMenu.upgradeIcons[3].getPosition().x + buyMenu.upgradeIcons[3].getSize().x && mouseY >= buyMenu.upgradeIcons[3].getPosition().y && mouseY <= buyMenu.upgradeIcons[3].getPosition().y + buyMenu.upgradeIcons[3].getSize().y)
            {
                //Tractor upgrade
                //SoundEffect.PURCHASEITEM.play();
                
                if(upgrade4Bought == false)
                {
                    returnValue = 3;
                    decrementValue = 20;

                    if(resourceMenu.getIndexVal(4) >= decrementValue)
                    {
                        System.out.println("test3");
                        upgrade4Bought = true;
    
                        resourceMenu.autoIncrement = true;
                    }
                }
                else
                {
                    returnValue = 3;
                    decrementValue = 10;

                    if(resourceMenu.getIndexVal(4) >= decrementValue)
                    {
                        System.out.println("test3.1");
                        upgrade4Bought = true;
    
                        resourceMenu.autoIncrement = true;
                        resourceMenu.autoIncrementVal++;
                    }
                }

            }
    
            if(returnValue != -1 && buyMenu.menuOpen == true && decrementValue != 0)
            {
                if(resourceMenu.getIndexVal(4) >= decrementValue)
                {
                    //resourceMenu.increment(returnValue);
                    this.unlock(returnValue + 4);
                    resourceMenu.decrement(4, decrementValue);
                    SoundEffect.PURCHASEITEM.play();
                }
            }
        }

        return returnValue;
    }

    /**
     * Sets the field that is currently selected to grow if a seed type is also currently selected.
     */
    public void selectVegToGrowOnField()
    {
        Fields temp = fieldMatrix[0][0].selectedField;
        if(temp != null && resourceMenu.getSelectedIndex() != -1 && temp != game.farmFields[2][2] && resourceMenu.getIndexVal(resourceMenu.getSelectedIndex()) > 0 && temp.unlocked == true)
        {
            System.out.println("testing1");
            //Checks if there is anything already growing on the field selected, and if so doesnt allow for selection
            if(temp.growing == false && temp.readyToCollect == false)
            {
                System.out.println("testing2");
                if(resourceMenu.getSelectedIndex() == 0)
                {
                    temp.setVegType(0);
                }
                else if(resourceMenu.getSelectedIndex() == 1)
                {
                    temp.setVegType(1);
                }
                else if(resourceMenu.getSelectedIndex() == 2)
                {
                    temp.setVegType(2);
                }
                else if(resourceMenu.getSelectedIndex() == 3)
                {
                    temp.setVegType(3);
                }
                
                SoundEffect.CROPPLANT.play();

                //Unselects the currently selected field
                if(temp.growing)
                {
                    fieldMatrix[0][0].selectedField = null;
                }
            }
        }
    }

    /**
     * Allows for user to purchase fields which are currently locked.
     */
    public void unlockField()
    {
        Fields temp = fieldMatrix[0][0].selectedField;
        if(temp != null && temp != fieldMatrix[2][2] && temp.unlocked == false && resourceMenu.getIndexVal(4) >= 10) // cause field 12 is behind the shop TODO: Remove field12 safely
        {
            temp.unlocked = true;
            temp.loadPathToRectangle("BoringGame/AllResources", "WetDirt.png");
            fieldMatrix[0][0].selectedField = null;
            resourceMenu.decrement(4, 10);
            SoundEffect.PURCHASEITEM.play();
        }             
    }

    /**
     * Detects the clicks on the specific fields for collection.
     */
    public void collectVeg()
    {
        if(buyMenu.menuOpen == false /*&& resourceMenu.getSelectedIndex() == -1*/)
        {
            for(int i = 0; i < 5; i++)
            {
                for(int z = 0; z < 5; z++)
                {
                    if(fieldMatrix[i][z].isClicked(window) && fieldMatrix[i][z] != fieldMatrix[2][2]) // cause field 12 is behind the shop TODO: Remove field12 safely
                    {
                        if(fieldMatrix[i][z].readyToCollect == false)
                        {
                            pause();
                        }
                        else
                        {
                            //Algorithm for collecting veg from a specific field
                            if(fieldMatrix[i][z].getVegType() != "" && checkProximityToField(fieldMatrix[i][z]) && resourceMenu.getSelectedIndex() == -1)//TODO: ADD IN LINE REGARDING CHECKING FOR PROXIMITY TO FIELD
                            {
                                if(fieldMatrix[i][z].getVegType() == "Hemp")
                                {
                                    SoundEffect.CROPHARVEST.play();
                                    resourceMenu.increment(4, hempIncrementVal);
                                }
                                else if(fieldMatrix[i][z].getVegType() == "Chilli")
                                {
                                    SoundEffect.CROPHARVEST.play();
                                    resourceMenu.increment(4, chilliIncrementVal);
                                }
                                else if(fieldMatrix[i][z].getVegType() == "Cauliflower")
                                {
                                    SoundEffect.CROPHARVEST.play();
                                    resourceMenu.increment(4, cauliflowerIncrementVal);
                                }
                                else if(fieldMatrix[i][z].getVegType() == "Carrot")
                                {
                                    SoundEffect.CROPHARVEST.play();
                                    resourceMenu.increment(4, carrotIncrementVal);
                                }
    
                                fieldMatrix[i][z].setVegType("");
                                fieldMatrix[i][z].selectedField = null;
                                fieldMatrix[i][z].readyToCollect = false;
                                fieldMatrix[i][z].growing = false;
                                
                                fieldMatrix[i][z].loadPathToRectangle("BoringGame/AllResources", "WetDirt.png");
                                pause();
                            }
    
                        }
                    }
                }
            }
        }
    }

    public void eventClicked(float mouseX, float mouseY) {
        if(resourceMenu.getIndexVal(4) >= 10 && familyMenu.eventClicked(mouseX, mouseY)) {
            resourceMenu.decrement(4, 10);
        }
    }

    /**
     * Slows down the game by sleeping for 1/4 of a second
     */
    public void pause()
    {
        try 
        {
            TimeUnit.MILLISECONDS.sleep(250); 
        } 
        catch (Exception e) 
        {

        }
    }

    /**
     * Checks if the farmer is close enough to the field to be capable of collecting what is growing on said field.
     * @param field the field we want to collect from
     * @param farmer the farmer object
     * @return boolean giving or not authorazation to collect
     */
    public boolean checkProximityToField(Fields field)
    {
        boolean returnVal = false;

        Vector2f fieldSize = field.getSize();
        Vector2f fieldPos = field.getPosition();

        //Vector2f farmerSize = farmer.getSize();
        Vector2f farmerPos = game.farmer.getPosition();

        double distBetweenFarmerAndField = Math.sqrt((farmerPos.x - fieldPos.x) * (farmerPos.x - fieldPos.x) + (farmerPos.y - fieldPos.y) * (farmerPos.y - fieldPos.y));

        if(distBetweenFarmerAndField <= game.fieldSizeInt)
        {
            returnVal = true;
        }

        return returnVal;
    }

    public void unlock(int i)
    {
        
        if(i == 1)
        {
            buyMenu.counterText[i].setString("2");
            carrotUnlocked = true;
        }
        else if(i == 2 && carrotUnlocked == true)
        {
            buyMenu.counterText[i].setString("5");
            hempUnlocked = true;
        }
        else if(i == 3 && hempUnlocked == true)
        {
            buyMenu.counterText[i].setString("13");
            cauliflowerUnlocked = true;
        }
        else if(i == 4)
        {
            buyMenu.counterText[i].setString("");
            Loader.loadPathToRectangle("BoringGame/AllResources/Closeup Upgrades", "upgrade" + (i - 4) + ".png", buyMenu.upgradeIcons[i - 4], buyMenu.upgradeTextures[i - 4]);
        }
        else if(i == 5)
        {
            buyMenu.counterText[i].setString("");
            Loader.loadPathToRectangle("BoringGame/AllResources/Closeup Upgrades", "upgrade" + (i - 4) + ".png", buyMenu.upgradeIcons[i - 4], buyMenu.upgradeTextures[i - 4]);
        }
        else if(i == 6)
        {
            buyMenu.counterText[i].setString("");
            Loader.loadPathToRectangle("BoringGame/AllResources/Closeup Upgrades", "upgrade" + (i - 4) + ".png", buyMenu.upgradeIcons[i - 4], buyMenu.upgradeTextures[i - 4]);
        }
        else if(i == 7)
        {
            buyMenu.counterText[i].setString("10");
            Loader.loadPathToRectangle("BoringGame/AllResources/Closeup Upgrades", "upgrade" + (i - 4) + ".png", buyMenu.upgradeIcons[i - 4], buyMenu.upgradeTextures[i - 4]);
        }
    }

}