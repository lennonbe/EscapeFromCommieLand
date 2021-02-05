package BoringGame;

import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.window.*;
import org.jsfml.window.event.*;
import java.util.concurrent.TimeUnit;

public class BuyCycle 
{
    protected Fields[] fieldArray;
    protected ResourceMenu resourceMenu;
    protected BuyMenu buyMenu; 
    protected RenderWindow window;
    protected Game game;
    protected boolean upgrade1Bought = false, upgrade2Bought = false, upgrade3Bought = false;
    protected int hempIncrementVal = 13, chilliIncrementVal = 2, cauliflowerIncrementVal = 53, carrotIncrementVal = 5;
    protected int hempCost = 5, chilliCost = 1, cauliflowerCost = 13, carrotCost = 2;

    //Time increasing values for upgrades
    private double upgrade2 = 0.75;
    private double upgrade3 = 0.65;

    public BuyCycle(Fields[] array, ResourceMenu resMenu, BuyMenu buyMenuInput, RenderWindow windowInput, Game gameInput)
    {
        fieldArray = array;
        resourceMenu = resMenu;
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
                //SoundEffect.PURCHASEITEM.play();
                returnValue = 0;
                decrementValue = chilliCost;
            }
            else if(mouseX >= buyMenu.vegIcons[1].getPosition().x && mouseX <= buyMenu.vegIcons[1].getPosition().x + buyMenu.vegIcons[1].getSize().x && mouseY >= buyMenu.vegIcons[1].getPosition().y && mouseY <= buyMenu.vegIcons[1].getPosition().y + buyMenu.vegIcons[1].getSize().y)
            {
                //SoundEffect.PURCHASEITEM.play();
                returnValue = 1;
                decrementValue = carrotCost;
            }
            else if(mouseX >= buyMenu.vegIcons[2].getPosition().x && mouseX <= buyMenu.vegIcons[2].getPosition().x + buyMenu.vegIcons[2].getSize().x && mouseY >= buyMenu.vegIcons[2].getPosition().y && mouseY <= buyMenu.vegIcons[2].getPosition().y + buyMenu.vegIcons[2].getSize().y)
            {
                //SoundEffect.PURCHASEITEM.play();
                returnValue = 2;
                decrementValue = hempCost;
            }
            else if(mouseX >= buyMenu.vegIcons[3].getPosition().x && mouseX <= buyMenu.vegIcons[3].getPosition().x + buyMenu.vegIcons[3].getSize().x && mouseY >= buyMenu.vegIcons[3].getPosition().y && mouseY <= buyMenu.vegIcons[3].getPosition().y + buyMenu.vegIcons[3].getSize().y)
            {
                //SoundEffect.PURCHASEITEM.play();
                returnValue = 3;
                decrementValue = cauliflowerCost;
            }
    
            if(returnValue != -1 && buyMenu.menuOpen == true && decrementValue != 0)
            {
                if(resourceMenu.getIndexVal(4) >= decrementValue)
                {
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
                //Scarecrow upgrade
                //SoundEffect.PURCHASEITEM.play();
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
            else if(upgrade1Bought == true && upgrade2Bought == false && mouseX >= buyMenu.upgradeIcons[1].getPosition().x && mouseX <= buyMenu.upgradeIcons[1].getPosition().x + buyMenu.upgradeIcons[1].getSize().x && mouseY >= buyMenu.upgradeIcons[1].getPosition().y && mouseY <= buyMenu.upgradeIcons[1].getPosition().y + buyMenu.upgradeIcons[1].getSize().y)
            {
                //Watering system upgrade
                //SoundEffect.PURCHASEITEM.play();
                returnValue = 1;
                decrementValue = 10;
                
                if(resourceMenu.getIndexVal(4) >= decrementValue)
                {
                    System.out.println("test2");
                    upgrade2Bought = true;

                    //TODO: Increase speed of growth with this upgrade
                    for(int i = 0; i < fieldArray.length; i++)
                    {
                        for(int j = 0; j < fieldArray[i].clockArr.length; j++)
                        {
                            int temp = fieldArray[i].clockArr[j].time.getInitialDelay();
                            fieldArray[i].clockArr[j].time.setDelay((int)(temp * upgrade2));
                        }
                    }
                }


            }
            else if(upgrade1Bought == true && upgrade2Bought == true && upgrade3Bought == false && mouseX >= buyMenu.upgradeIcons[2].getPosition().x && mouseX <= buyMenu.upgradeIcons[2].getPosition().x + buyMenu.upgradeIcons[2].getSize().x && mouseY >= buyMenu.upgradeIcons[2].getPosition().y && mouseY <= buyMenu.upgradeIcons[2].getPosition().y + buyMenu.upgradeIcons[2].getSize().y)
            {
                //Tractor upgrade
                //SoundEffect.PURCHASEITEM.play();
                returnValue = 2;
                decrementValue = 15;
                
                if(resourceMenu.getIndexVal(4) >= decrementValue)
                {
                    System.out.println("test3");
                    upgrade3Bought = true;

                    //TODO: Increase both speed and profit with this upgrade
                    for(int i = 0; i < fieldArray.length; i++)
                    {
                        for(int j = 0; j < fieldArray[i].clockArr.length; j++)
                        {
                            int temp = fieldArray[i].clockArr[j].time.getInitialDelay();
                            fieldArray[i].clockArr[j].time.setDelay((int)(temp * upgrade3));
                        }
                    }
    
                    carrotIncrementVal += 2;
                    chilliIncrementVal += 2;
                    hempIncrementVal += 2;
                    cauliflowerIncrementVal += 2;
                }

            }
    
            if(returnValue != -1 && buyMenu.menuOpen == true && decrementValue != 0)
            {
                if(resourceMenu.getIndexVal(4) >= decrementValue)
                {
                    //resourceMenu.increment(returnValue);
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
        Fields temp = fieldArray[1].selectedField;
        if(temp != null && resourceMenu.getSelectedIndex() != -1 && temp != game.farmFields[12] && resourceMenu.getIndexVal(resourceMenu.selectedIndex) > 0)
        {
            //Checks if there is anything already growing on the field selected, and if so doesnt allow for selection
            if(temp.growing == false && temp.readyToCollect == false)
            {
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

                //Unselects the currently selected to grow field
                if(temp.growing)
                {
                    fieldArray[1].selectedField = null;
                }
            }
        }
    }

    /**
     * Detects the clicks on the specific fields for collection.
     */
    public void collectVeg()
    {
        if(buyMenu.menuOpen == false /*&& resourceMenu.getSelectedIndex() == -1*/)
        {
            for(int i = 0; i < fieldArray.length; i++)
            {
                if(fieldArray[i].isClicked(window) && i != 12) // cause field 12 is behind the shop TODO: Remove field12 safely
                {
                    if(fieldArray[i].readyToCollect == false)
                    {
                        pause();
                    }
                    else
                    {
                        //Algorithm for collecting veg from a specific field
                        if(fieldArray[i].getVegType() != "" && checkProximityToField(fieldArray[i]) && resourceMenu.getSelectedIndex() == -1)//TODO: ADD IN LINE REGARDING CHECKING FOR PROXIMITY TO FIELD
                        {
                            if(fieldArray[i].getVegType() == "Hemp")
                            {
                                SoundEffect.CROPHARVEST.play();
                                resourceMenu.increment(4, hempIncrementVal);
                            }
                            else if(fieldArray[i].getVegType() == "Chilli")
                            {
                                SoundEffect.CROPHARVEST.play();
                                resourceMenu.increment(4, chilliIncrementVal);
                            }
                            else if(fieldArray[i].getVegType() == "Cauliflower")
                            {
                                SoundEffect.CROPHARVEST.play();
                                resourceMenu.increment(4, cauliflowerIncrementVal);
                            }
                            else if(fieldArray[i].getVegType() == "Carrot")
                            {
                                SoundEffect.CROPHARVEST.play();
                                resourceMenu.increment(4, carrotIncrementVal);
                            }

                            fieldArray[i].setVegType("");
                            fieldArray[i].selectedField = null;
                            fieldArray[i].readyToCollect = false;
                            fieldArray[i].growing = false;
                            
                            fieldArray[i].loadPathToRectangle("BoringGame", "DirtWet.png");
                            pause();
                        }

                    }
                }
            }
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
}