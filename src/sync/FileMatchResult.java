/**
 * Sync 2.1 Copyright 2007 Zach Scrivena 2007-12-09 zachscrivena@gmail.com
 * http://syncdir.sourceforge.net/
 *
 * Sync performs one-way directory or file synchronization.
 *
 * TERMS AND CONDITIONS: This program is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package sync;

/**
 * Represent a sourceFilePath or directory.
 */
class FileMatchResult {

    /**
     * File object representing this sourceFilePath/directory (full canonical
     * pathname)
     */
    final String sourceFilePath;

    final String targetFilePath;

    final matchResultType matchResult;

    /**
     * enum type: logic applied to this node's children (AND, NAND, OR, NOR)
     */
    public static enum matchResultType {

        ADDED, MODIFIED, RENAMED, DELETED, MATCHED
    };

    public String getSourceFilePath() {
        return sourceFilePath;
    }

    public String getTargetFilePath() {
        return targetFilePath;
    }

    public matchResultType getMatchResult() {
        return matchResult;
    }

    @Override
    public String toString() {
        if (this.matchResult == FileMatchResult.matchResultType.DELETED) {
            return getMatchResultString(this.matchResult) + "  " + this.targetFilePath;
        } else if (this.matchResult == FileMatchResult.matchResultType.RENAMED) {
            return getMatchResultString(this.matchResult) + "  " + this.sourceFilePath + " --> " + this.targetFilePath;
        } else {
            return getMatchResultString(this.matchResult) + "  " + this.sourceFilePath;
        }
    }

    public String getMatchResultString(matchResultType matchResult) {
        String str;
        switch (matchResult) {
            case ADDED:
                str = "new ";
                break;
            case MODIFIED:
                str = "modified ";
                break;
            case RENAMED:
                str = "renamed ";
                break;
            case DELETED:
                str = "deleted";
                break;
            case MATCHED:
                str = "matched";
                break;
            default:
                str = "matched";
        }
        return str;
    }

    public FileMatchResult(String fileName, String targetFilePath, matchResultType matchResult) {
        this.sourceFilePath = fileName;
        this.targetFilePath = targetFilePath;
        this.matchResult = matchResult;
    }

//    /**
//     * 
//     * @param sourceFilePath
//     * @param matchResult 
//     */
//    FileMatchResult(final String sourceFilePath, String matchResult) {
//        this.sourceFilePath = sourceFilePath;
//        this.targetFilePath = "";
//        this.matchResult = matchResult;
//    }
}
