package com.ale059.escapegameplasticineworld1;

import java.util.ArrayList;

public class APuzzle_Code extends APuzzle {

    protected static String SYMBOLS_SET_ABC = "abcdefghijklmnopqrstuvwxyz";
    protected static String SYMBOLS_SET_123 = "0123456789";

    protected String[] SymbolsSets = null;
    public int LogoResID = 0;

        public APuzzle_Code(){
            Init();
        }

        public void Init()
        {
            AnswerCode = "11";
            InitialCode = "00";
            CurrentCode = InitialCode;

            SymbolsSets = new String[4];
            SymbolsSets[0] = SymbolsSets[1] = SymbolsSets[2] = SymbolsSets[3] = SYMBOLS_SET_123;
            //SymbolsSets[4] = APuzzle.SYMBOLS_SET_123;

        }

        protected int getResourceForChar(char ch) {
            switch (ch) {
                case '0': return R.drawable.abc_0;
                case '1': return R.drawable.abc_1;
                case '2': return R.drawable.abc_2;
                case '3': return R.drawable.abc_3;
                case '4': return R.drawable.abc_4;
                case '5': return R.drawable.abc_5;
                case '6': return R.drawable.abc_6;
                case '7': return R.drawable.abc_7;
                case '8': return R.drawable.abc_8;
                case '9': return R.drawable.abc_9;

                case 'a': return R.drawable.abc_a;
                case 'b': return R.drawable.abc_b;
                case 'c': return R.drawable.abc_c;
                case 'd': return R.drawable.abc_d;
                case 'e': return R.drawable.abc_e;
                case 'f': return R.drawable.abc_f;
                case 'g': return R.drawable.abc_g;
                case 'h': return R.drawable.abc_h;
                case 'i': return R.drawable.abc_i;
                case 'j': return R.drawable.abc_j;
                case 'k': return R.drawable.abc_k;
                case 'l': return R.drawable.abc_l;
                case 'm': return R.drawable.abc_m;
                case 'n': return R.drawable.abc_n;
                case 'o': return R.drawable.abc_o;
                case 'p': return R.drawable.abc_p;
                case 'q': return R.drawable.abc_q;
                case 'r': return R.drawable.abc_r;
                case 's': return R.drawable.abc_s;
                case 't': return R.drawable.abc_t;
                case 'u': return R.drawable.abc_u;
                case 'v': return R.drawable.abc_v;
                case 'w': return R.drawable.abc_w;
                case 'x': return R.drawable.abc_x;
                case 'y': return R.drawable.abc_y;
                case 'z': return R.drawable.abc_z;
            }
            return 0;
        }

        @Override
        protected AScenePuzzle createScene()
        {
            return new AScenePuzzle_Code(this);
        }

        public class AScenePuzzle_Code extends AScenePuzzle {

            public int LogoResID = 0;
            protected ArrayList<ASprite> mListBtnUp = new ArrayList<ASprite>();
            protected ArrayList<ASprite> mListBtnSymb = new ArrayList<ASprite>();
            protected ArrayList<ASprite> mListBtnDown = new ArrayList<ASprite>();

            protected APuzzle_Code Puzzle_Code = null;


            public AScenePuzzle_Code(APuzzle_Code poPuzzle) {
                Puzzle = poPuzzle;
                Puzzle_Code = poPuzzle;

                ASprite oSprite = null;
                addSprite("bg", R.drawable.bg_black_shade, 0, 0, 1024, 1024, true, false);
                addSprite("", poPuzzle.LogoResID, (ViewMain.SIZE_IN_MEMORY - 160)/2, 50, 160, 160, true, false);

                int nLen = poPuzzle.getCodeLength();
                int x0 = 0, y0 = 0, spx = 40, spy = 40;
                x0 = (ViewMain.SIZE_IN_MEMORY - (nLen * 160 + (nLen - 1) * spx)) / 2;
                y0 = (ViewMain.SIZE_IN_MEMORY - (160 + 2 * spy + 2 * 116)) / 2;

                int x1 = x0;
                for (int i = 0; i < nLen; i++) {
                    int y1 = y0;
                    mListBtnUp.add(addSprite("symbol_up", R.drawable.arrow_up, x1+23, y1, 114, 116, true, true));
                    y1 = y1 + 116 + spy;
                    addSprite("symbol_bg", R.drawable.puz_symb_bg, x1, y1, 160, 166, true, false);
                    char ch = poPuzzle.CurrentCode.charAt(i);
                    int nResID = this.getResourceForChar(ch);
                    mListBtnSymb.add(addSprite("symbol", nResID, x1+10, y1+10, 160-2*10, 166-2*10, true, true));
                    y1 = y1 + 160 + spy;
                    mListBtnDown.add(addSprite("symbol_down", R.drawable.arrow_down, x1+23, y1, 114, 116, true, true));
                    x1 = x1 + 160 + spx;
                }

                addSprite("btn_check", R.drawable.puz_btn_long, x0, y0 + (160 + 2 * spy + 2 * 116) + spy, (nLen * 160 + (nLen - 1) * spx), 2*spy, true, true);


                addSprite("exit", R.drawable.inv_down, 422, 904, true, true);
            }

            protected int getResourceForChar(char ch) {
                if (Puzzle_Code != null)
                    return Puzzle_Code.getResourceForChar(ch);
                return 0;
            }

            protected void changeSymbol(int pnPos, int pnDirection) {
                char[] chars = CurrentCode.toCharArray();
                int nSymbPos = SymbolsSets[pnPos].indexOf(chars[pnPos]);
                int nMaxPos = SymbolsSets[pnPos].length() - 1;
                //if (nPos<0) nPos=0;
                nSymbPos += pnDirection;
                if (nSymbPos < 0) nSymbPos = nMaxPos;
                if (nSymbPos > nMaxPos) nSymbPos = 0;
                chars[pnPos] = SymbolsSets[pnPos].charAt(nSymbPos);
                CurrentCode = new String(chars);

                ASprite oSprite = mListBtnSymb.get(pnPos);
                if (oSprite != null)
                    oSprite.setResourceID( getResourceForChar( CurrentCode.charAt(pnPos) ) );
            }

            @Override
            public void onSpriteTouch(ASprite poSprite, int pX, int pY) {
                if (poSprite == null)
                    return;
                String sID = poSprite.ID;
                if (sID.equals("exit")) {
                    app.egHidePuzzle();
                } else if (sID.equals("btn_check")) {
                    if (Puzzle != null) {
                        if (Puzzle.isAnswerCorrect() == true)
                            Puzzle.onPuzzleSolved();
                        else
                            Puzzle.onPuzzleFailed();
                    }

                } else {
                    int nPos = mListBtnUp.indexOf(poSprite);
                    if (nPos != -1)
                        changeSymbol(nPos, +1);
                    nPos = mListBtnDown.indexOf(poSprite);
                    if (nPos != -1)
                        changeSymbol(nPos, -1);

                }
            }

        }
    }
